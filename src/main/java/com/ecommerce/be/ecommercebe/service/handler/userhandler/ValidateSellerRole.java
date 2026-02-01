package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;

public class ValidateSellerRole extends Handler<UserResponse> {
    @Override
    protected ValidateResult<UserResponse> doValidate(UserResponse object) {
        if(object.getRoles().contains("SELLER")){
            return ValidateResult.fail("User is already a seller");
        }

        return ValidateResult.success(object);
    }
}
