package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.CartDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.CartItemDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.CartItemResponse;
import com.ecommerce.be.ecommercebe.dto.response.CartResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.CartItemMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.CartMapper;
import com.ecommerce.be.ecommercebe.model.*;
import com.ecommerce.be.ecommercebe.repository.CartItemRepository;
import com.ecommerce.be.ecommercebe.repository.CartRepository;
import com.ecommerce.be.ecommercebe.repository.ProductVariantRepository;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CartItemRepository itemRepository;

    private final CartMapper cartMapper;
    private final CartItemMapper itemMapper;

    private final UserService userService;
    private final ProductService productService;

    @CachePut(value = "carts", key = "#result.id")
    public CartResponse createCart(CartDTORequest dto) {
        CartEntity cartEntity = refreshCart(dto.getUserId());

        // Cart Items
        for (CartItemDTORequest itemDto : dto.getCartItems()) {
            processAddItemToCart(cartEntity, itemDto);
        }

        // ? Save and Response data
        CartEntity saved = cartRepository.save(cartEntity);
        CartResponse cartResponse = cartMapper.toDTO(saved);

        return cartResponse;
    }

    @CacheEvict(value = "carts", key = "#result.id")
    public CartEntity refreshCart(Long id) {
        UserEntity user = userService.getUser(id);
        CartEntity cartEntity = getCart(user.getId());

        cartEntity.getCartItems().clear();

        return cartEntity;
    }

    protected CartEntity getCart(Long id) {
        return cartRepository.findById(id)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    UserEntity user = userService.getUser(id);
                    newCart.setUser(user);
                    newCart.setId(user.getId());
                    return newCart;
                });
    }

    @Cacheable(value = "carts", key = "#id")
    public CartResponse getCartDetail(Long id) {
        return cartMapper.toDTO(getCart(id));
    }

    @CachePut(value = "carts", key = "#dto.userId")
    public CartResponse syncCart(CartDTORequest dto) {
        UserEntity user = userService.getUser(dto.getUserId());
        CartEntity cart = getCart(user.getId());

        Map<Long, CartItemEntity> currentItems = cart.getCartItems().stream()
                .collect(Collectors.toMap(item -> item.getVariant().getId(), item -> item));
        Set<Long> updatedVariantIds = new HashSet<>();
        Set<Long> variantIdsToDelete = new HashSet<>();

        for (CartItemDTORequest cartItem : dto.getCartItems()) {
            Long variantId = cartItem.getVariantId();
            updatedVariantIds.add(variantId);

            // Remove item has quantity <= 0
            if (cartItem.getQuantity() <= 0) {
                variantIdsToDelete.add(variantId);
                continue;
            }
            // Update Cart
            if (currentItems.containsKey(variantId)) {
                CartItemEntity currentItem = currentItems.get(variantId);
                currentItem.setQuantity(cartItem.getQuantity());
            } else {
                processAddItemToCart(cart, cartItem);
            }
        }

        List<Long> idsToHardDelete = cart.getCartItems().stream()
                .filter(item -> !updatedVariantIds.contains(item.getVariant().getId())
                        || variantIdsToDelete.contains(item.getVariant().getId()))
                .map(BaseEntity::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Remove cart item not in update list or in delete list
        cart.getCartItems().removeIf(item -> !updatedVariantIds.contains(item.getVariant().getId()) ||
                variantIdsToDelete.contains(item.getVariant().getId()));

        if (!idsToHardDelete.isEmpty()) {
            itemRepository.hardDeleteAllByIds(idsToHardDelete);
        }
        CartEntity saved = cartRepository.save(cart);
        return cartMapper.toDTO(saved);
    }

    private void processAddItemToCart(CartEntity cart, CartItemDTORequest dto) {
        ProductVariantEntity variant = productService.getVariant(dto.getVariantId());

        cart.getCartItems().stream()
                .filter(item -> item.getVariant().getId().equals(dto.getVariantId()))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> {
                            existingItem.setQuantity(dto.getQuantity());
                            existingItem.setPriceAtAdd(calculatePrice(variant));
                        },
                        () -> {
                            CartItemEntity newItem = itemMapper.toEntity(dto);
                            newItem.setVariant(variant);
                            newItem.setPriceAtAdd(calculatePrice(variant));
                            cart.addCartItems(newItem);
                        });
    }

    @CacheEvict(value = "carts", key = "#userId")
    public void clearCartCache(Long userId) {
        logger.info("[CART_SERVICE][clearCartCache] Clear cart cache for user {}", userId);
    }

    private BigDecimal calculatePrice(ProductVariantEntity variant) {
        return (variant.getSalePrice() != null) ? variant.getSalePrice() : variant.getPrice();
    }

}
