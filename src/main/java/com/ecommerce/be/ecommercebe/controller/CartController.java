package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.BrandDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.CartDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.BrandResponse;
import com.ecommerce.be.ecommercebe.dto.response.CartResponse;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    @PutMapping
    public ResponseData<CartResponse> syncCart(@Valid @RequestBody CartDTORequest request){
        logger.info("[CART_CONTROLLER][syncCart] Sync cart for user {}", request.getUserId());
        CartResponse response = cartService.syncCart(request);

        return new ResponseData<>("Cart " + response.getId() + " is updated", HttpStatus.OK.value(), response);
    }

    @GetMapping("/{userId}")
    public ResponseData<CartResponse> getCart(@PathVariable Long userId){
        logger.info("[CART_CONTROLLER][getCart] Get cart for user {}", userId);
        CartResponse response = cartService.getCartDetail(userId);

        return new ResponseData<>("Cart " + response.getId() + " is created", HttpStatus.ACCEPTED.value(), response);
    }
}
