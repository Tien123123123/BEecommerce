package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.ShopDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.ShopResponse;
import com.ecommerce.be.ecommercebe.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{user_id}/shop")
@RequiredArgsConstructor
public class ShopController {
    private static final Logger logger = LoggerFactory.getLogger(ShopController.class);
    private final ShopService shopService;

    /**
     * - Description: Create Shop
     * - Method Post
     * - API: /api/{user_id}/shop
     **/
    @PostMapping
    public ResponseData<ShopResponse> createShop(@PathVariable Long user_id,
            @Valid @RequestBody ShopDTORequest shopDTO) {
        logger.info("[SHOP_CONTROLLER] Create Shop: {}", shopDTO.getShopName());
        ShopResponse shop = shopService.createShop(shopDTO, user_id);

        return new ResponseData<>("Created Shop " + shopDTO.getShopName(), HttpStatus.CREATED.value(), shop);
    }

    /**
     * - Description: Get Shop
     * - Method Get
     * - API: /api/{user_id}/shop
     **/
    @GetMapping
    public ResponseData<ShopResponse> getShopDetail(@PathVariable Long user_id) {
        logger.info("[SHOP_CONTROLLER] Get Shop from User: {}", user_id);
        ShopResponse shop = shopService.getShopDetail(user_id);

        return new ResponseData<>("Get Shop " + shop.getShopName(), HttpStatus.ACCEPTED.value(), shop);
    }

    /**
     * - Description: Delete Shop
     * - Method Delete
     * - API: /api/{user_id}/shop
     **/
    @DeleteMapping
    public ResponseData<ShopResponse> deleteShop(@PathVariable Long user_id) {
        logger.info("[SHOP_CONTROLLER] Delete Shop from User: {}", user_id);
        shopService.deleteShop(user_id);

        return new ResponseData<>("Delete successfully Shop " + user_id, HttpStatus.ACCEPTED.value(), null);
    }

    /**
     * - Description: Restore Shop
     * - Method Patch
     * - API: /api/{user_id}/shop/restore/{id}
     **/
    @PatchMapping("/restore")
    public ResponseData<ShopResponse> restoreShop(@PathVariable Long user_id) {
        logger.info("[SHOP_CONTROLLER] Restore Shop for User: {}", user_id);
        ShopResponse shop = shopService.restoreShop(user_id);

        return new ResponseData<>("Get Shop " + shop.getShopName(), HttpStatus.ACCEPTED.value(), shop);
    }
}
