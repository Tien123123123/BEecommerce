package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(1)
public class ValidateValidPassword extends Handler<UserRegisterDTORequest> {

    @Override
    protected ValidateResult<UserRegisterDTORequest> doValidate(UserRegisterDTORequest object) {
        if(!object.getPassword().equals(object.getConfirm_password())){
            return ValidateResult.fail("Password and Confirm Password unmatch!");
        }

        return ValidateResult.success(object);
    }
}
