package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.UserService;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;
/**
 Validate if user is banned (soft-delete) or not
 - Input: userDTO
 - Output: success
 **/
@RequiredArgsConstructor
public class ValidateUserStatus extends Handler<UserResponse> {
    @Override
    protected ValidateResult<UserResponse> doValidate(UserResponse object) {
        if(object.getSoftDelete()){
            return ValidateResult.fail("User " + object.getUsername() + " is deleted!");
        }

        return ValidateResult.success(object);
    }
}
