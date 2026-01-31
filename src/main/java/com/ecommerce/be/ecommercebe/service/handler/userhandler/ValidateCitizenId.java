package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidateCitizenId extends UserHandler<UserResponse>{
    private final String citizenIdentity;
    @Override
    protected UserCheckResult<?> checkValid(UserResponse object) {
        if(citizenIdentity.length() < 12){
            UserCheckResult.fail("Invalid Citizen Identity!");
        }

        return UserCheckResult.success(object);
    }
}
