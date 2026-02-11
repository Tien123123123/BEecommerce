package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final SellerService sellerService;

    /**
     * - Description: Promote User (Buyer) to Seller
     * - Method Patch
     * - API: /api/seller/id
     **/
    @PatchMapping("/{id}")
    public ResponseData<SellerResponse> promoteToSeller(@PathVariable Long id,
            @Valid @RequestBody SellerRegisterDTORequest sellerDTO) {
        logger.info("[SELLER_CONTROLLER][Promote Seller] promote User {} to Seller", id);
        SellerResponse seller = sellerService.promoteToSeller(id, sellerDTO);

        return new ResponseData<>("Promoted User " + id + " to Seller!", HttpStatus.CREATED.value(), seller);
    }

    /**
     * - Description: Get Seller details
     * - Method Get
     * - API: /api/user/id
     **/
    @GetMapping("/{id}")
    public ResponseData<SellerResponse> getSeller(@PathVariable Long id) {
        logger.info("[SELLER_CONTROLLER][Get Seller] Get seller {}", id);
        SellerResponse seller = sellerService.getSellerDetails(id);

        return new ResponseData<>("Seller " + id + " detail!", HttpStatus.ACCEPTED.value(), seller);
    }

    /**
     * - Description: Create User
     * - Method Post
     * - API: /api/user
     **/
    @DeleteMapping("/{id}")
    public ResponseData<SellerResponse> deleteSeller(@PathVariable Long id) {
        logger.info("[SELLER_CONTROLLER][Delete Seller] Delete seller {}", id);
        sellerService.deleteSeller(id);

        return new ResponseData<>("Seller " + id + " deleted!", HttpStatus.ACCEPTED.value(), null);
    }

    /**
     * - Description: Restore Seller
     * - Method Patch
     * - API: /api/seller/restore/id
     **/
    @PatchMapping("/restore/{id}")
    public ResponseData<SellerResponse> restoreSeller(@PathVariable Long id) {
        logger.info("[SELLER_CONTROLLER][Restore Seller] Restore seller {}", id);
        SellerResponse seller = sellerService.restoreSeller(id);

        return new ResponseData<>("Seller " + id + " restored", HttpStatus.ACCEPTED.value(), seller);
    }
}
