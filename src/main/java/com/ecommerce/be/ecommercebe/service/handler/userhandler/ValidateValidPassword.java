package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;

public class ValidateValidPassword extends UserHandler<UserRegisterDTORequest> {

    @Override
    protected UserCheckResult<?> checkValid(UserRegisterDTORequest userDTO) {
        if(userDTO.getPassword().equals(userDTO.getConfirm_password())){
            return UserCheckResult.success(userDTO);
        }

        return UserCheckResult.fail("Invalid Password and Confirm Password");
    }
}
