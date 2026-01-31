package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckSellerStatus extends UserHandler<UserResponse>{
    private final UserService userService;

    @Override
    protected UserCheckResult<?> checkValid(UserResponse object) {
        UserEntity user = userService.getUser(object.getId());
        if(user.getSeller().isActive() && user.getRoles().contains("SELLER")){
            UserCheckResult.fail("User is already SELLER");
        }

        return UserCheckResult.success(object);
    }
}
