package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.UserImp;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(2)
public class ValidateUserExists extends Handler<UserRegisterDTORequest> {
    private final UserRepository userRepository;

    @Override
    protected ValidateResult<UserRegisterDTORequest> doValidate(UserRegisterDTORequest userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return ValidateResult.fail("User email exists: " + userDTO.getEmail());
        }
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            return ValidateResult.fail("User name exists: " + userDTO.getUsername());
        }

        return ValidateResult.success(userDTO);
    }
}
