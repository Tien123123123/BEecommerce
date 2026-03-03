package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.constant.OrderStatus;
import com.ecommerce.be.ecommercebe.dto.request.CartDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.CartItemDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.OrderDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.ShopOrderDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.OrderResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.OrderItemMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.OrderMapper;
import com.ecommerce.be.ecommercebe.model.*;
import com.ecommerce.be.ecommercebe.repository.CartItemRepository;
import com.ecommerce.be.ecommercebe.repository.OrderRepository;
import com.ecommerce.be.ecommercebe.service.calculate.PriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final UserService userService;
    private final ShopService shopService;
    private final PriceService priceService;

    private final OrderMapper orderMapper;
    private final OrderItemMapper itemMapper;
    private final CartService cartService;

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public List<OrderResponse> createOrder(OrderDTORequest dto) {
        UserEntity user = userService.getUser(dto.getUserId());

        List<Long> cartItemIds = dto.getCartItemIds();

        List<CartItemEntity> cartItems = cartItemRepository.findAllById(cartItemIds);
        Map<Long, List<CartItemEntity>> itemsByShop = cartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getVariant().getProduct().getShop().getId()));

        List<OrderEntity> orders = new ArrayList<>();
        itemsByShop.forEach((shopId, items) -> {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setShop(shopService.getShop(shopId));
            orderEntity.setUser(user);

            items.forEach(cartItem -> {
                OrderItemEntity orderItemEntity = itemMapper.toEntity(cartItem);
                orderItemEntity.setVariant(cartItem.getVariant());
                orderItemEntity.setSku(cartItem.getVariant().getSku());
                orderItemEntity.setProductName(cartItem.getVariant().getProduct().getProductName());

                orderItemEntity.setUnitPrice(cartItem.getPriceAtAdd());
                orderItemEntity.setSubTotal(calculateSubTotal(cartItem));

                orderEntity.addOrderItem(orderItemEntity);
            });

            BigDecimal subTotals = orderEntity.getOrderItems().stream()
                    .map(OrderItemEntity::getSubTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal shippingFee = priceService.calculateShippingFee(shopId, user.getId());
            BigDecimal discount = priceService.calculateDiscount(dto.getVoucherCode(), subTotals);

            orderEntity.setSubTotal(subTotals);
            orderEntity.setShippingFee(shippingFee);
            orderEntity.setDiscountAmount(discount);

            orderEntity.setTotalAmount(priceService.calculateTotalAmount(subTotals, discount, shippingFee));
            orders.add(orderEntity);
        });

        orderRepository.saveAll(orders);

        // Hard delete to bypass @SoftDelete and avoid "trash" IDs
        cartItemRepository.hardDeleteAllByIds(cartItemIds);
        // Evict cache to ensure fresh data for GET /api/cart
        cartService.clearCartCache(user.getId());

        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private BigDecimal calculateSubTotal(CartItemEntity cartItem) {
        BigDecimal quantity = BigDecimal.valueOf(cartItem.getQuantity());
        return cartItem.getPriceAtAdd().multiply(quantity);
    }

    private OrderEntity getOrder(Long shopId, Long orderId){
        return orderRepository.findByIdAndShop_Id(orderId, shopId)
                .orElseThrow(()-> new RuntimeException("Cannot find order " + orderId + " from" +
                "shop " + shopId));
    }
    public OrderResponse getOrderDetail(Long shopId, Long orderId){
        return orderMapper.toDTO(getOrder(shopId, orderId));
    }

    public OrderResponse updateOrderStatus(ShopOrderDTORequest dto){
        // Get Order
        OrderEntity orderEntity = getOrder(dto.getShopId(), dto.getOrderId());
        orderEntity.setStatus(dto.getStatus());

        // Kafka
        if(orderEntity.getStatus().equals(OrderStatus.APPROVE)){
            
        }


        return orderMapper.toDTO(orderEntity);
    }
}
