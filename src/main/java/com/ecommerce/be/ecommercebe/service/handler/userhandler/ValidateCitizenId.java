package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidateCitizenId extends Handler<UserResponse> {
    private final String citizenIdentity;
    @Override
    protected ValidateResult<UserResponse> doValidate(UserResponse object) {
        if(citizenIdentity.length() < 13){
            ValidateResult.fail("Invalid Citizen Identity!");
        }

        return ValidateResult.success(object);
    }
}
