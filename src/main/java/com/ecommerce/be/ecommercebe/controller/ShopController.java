package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.ShopDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.ShopResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ShopMapper;
import com.ecommerce.be.ecommercebe.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{user_id}/shop")
@RequiredArgsConstructor
public class ShopController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ShopService shopService;
    private final ShopMapper shopMapper;

    /**
     - Method Post
     **/
    @PostMapping
    public ResponseData<ShopResponse> createShop(@PathVariable Long user_id, @RequestBody ShopDTORequest shopDTO){
        logger.info("[SHOP_CONTROLLER] Create Shop: {}", shopDTO.getShopName());
        ShopResponse shop = shopService.createShop(shopDTO, user_id);

        return new ResponseData<>("Created Shop " + shopDTO.getShopName(), HttpStatus.CREATED.value(), shop);
    }
}
