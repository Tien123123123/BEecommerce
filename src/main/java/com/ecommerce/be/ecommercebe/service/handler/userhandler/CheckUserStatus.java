package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.UserService;
import lombok.RequiredArgsConstructor;
/**
 Validate if user is banned (soft-delete) or not
 - Input: userDTO
 - Output: success
 **/
@RequiredArgsConstructor
public class CheckUserStatus extends UserHandler<UserResponse>{
    private final UserService userService;

    @Override
    protected UserCheckResult<?> checkValid(UserResponse userDTO) {
        UserEntity user = userService.getUser(userDTO.getId());
        if(user.isSoft_delete() || user.isActive()){
            UserCheckResult.fail("User is deleted!");
        }

        return UserCheckResult.success(userDTO);
    }
}
