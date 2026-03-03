package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.OrderDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.ShopOrderDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.OrderResponse;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.service.CartService;
import com.ecommerce.be.ecommercebe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping
    public ResponseData<List<OrderResponse>> createOrder(@RequestBody OrderDTORequest request){
        logger.info("[ORDER_CONTROLLER][createOrder] Create order for user {}", request.getUserId());
        List<OrderResponse> response = orderService.createOrder(request);

        return new ResponseData<>("Create order", HttpStatus.CREATED.value(), response);
    }

    @GetMapping
    public ResponseData<OrderResponse> getOrder(@RequestBody ShopOrderDTORequest request){
        logger.info("[ORDER_CONTROLLER][getOrder] Get order {} for shop {}", request.getOrderId(), request.getShopId());
        OrderResponse response = orderService.getOrderDetail(request.getShopId(), request.getOrderId());

        return new ResponseData<>("Create order", HttpStatus.ACCEPTED.value(), response);
    }

    @PatchMapping
    public ResponseData updateOrderStatus(@RequestBody ShopOrderDTORequest request){
        logger.info("[ORDER_CONTROLLER][getOrder] Update order {} from shop {}", request.getOrderId(), request.getShopId());
        OrderResponse response = orderService.updateOrderStatus(request);

        return new ResponseData<>("Updated order status", HttpStatus.ACCEPTED.value(), response);
    }
}
