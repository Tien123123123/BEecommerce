package com.ecommerce.be.ecommercebe.service.handler.sellerhandler;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.SellerRepository;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@RequiredArgsConstructor
public class ValidateSellerStatus extends Handler<SellerRegisterDTORequest> {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    @Override
    protected ValidateResult<SellerRegisterDTORequest> doValidate(SellerRegisterDTORequest object) {
        UserEntity user = userRepository.findById(object.getId())
                .orElseThrow(()->new RuntimeException("User " + object.getId() + " not found!"));

        if(user.getRoles().contains("SELLER") || user.getSeller() != null){
            return ValidateResult.fail("User is already a seller!");
        }
        Boolean isDeleted = sellerRepository.isSoftDeleted(object.getId());
        if(Boolean.TRUE.equals(isDeleted) && isDeleted != null){
            return ValidateResult.fail("Seller is deleted!");
        }

        return ValidateResult.success(object);
    }
}
