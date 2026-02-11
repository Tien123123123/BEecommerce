package com.ecommerce.be.ecommercebe.service.handler.shophandler;

import com.ecommerce.be.ecommercebe.dto.request.ShopDTORequest;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import com.ecommerce.be.ecommercebe.repository.SellerRepository;
import com.ecommerce.be.ecommercebe.service.SellerService;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class ValidateValidSeller extends Handler<ShopDTORequest> {
    private final SellerRepository sellerRepository;

    @Override
    protected ValidateResult<ShopDTORequest> doValidate(ShopDTORequest object) {
        SellerEntity seller = sellerRepository.findByUserEntity_Id(object.getSellerId())
                .orElseThrow(()-> new RuntimeException("Seller " + object.getSellerId() + " is deleted or cannot found!"));

        return ValidateResult.success(object);
    }
}
