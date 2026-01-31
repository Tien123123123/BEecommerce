package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.UserImp;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidateUserExists extends UserHandler<UserRegisterDTORequest>{
    private final UserRepository userRepository;

    @Override
    protected UserCheckResult<?> checkValid(UserRegisterDTORequest userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return UserCheckResult.fail("User email exists: " + userDTO.getEmail());
        } else if (userRepository.existsByUsername(userDTO.getUsername())) {
            return UserCheckResult.fail("User name exists: " + userDTO.getUsername());
        }

        return UserCheckResult.success(userDTO);
    }
}
