package com.ecommerce.be.ecommercebe.service.handler.sellerhandler;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ValidateCitizenId extends Handler<SellerRegisterDTORequest> {
    @Override
    protected ValidateResult<SellerRegisterDTORequest> doValidate(SellerRegisterDTORequest object) {
        if(object.getCitizenIdentity().length() < 10){
            return ValidateResult.fail("Invalid Citizen Identity!");
        }

        return ValidateResult.success(object);
    }
}
