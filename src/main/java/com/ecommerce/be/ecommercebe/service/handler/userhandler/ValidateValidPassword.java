package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidateValidPassword extends Handler<UserRegisterDTORequest> {

    @Override
    protected ValidateResult<UserRegisterDTORequest> doValidate(UserRegisterDTORequest object) {
        if(object.getPassword().equals(object.getConfirm_password())){
            return ValidateResult.fail("Invalid Password!");
        }

        return ValidateResult.success(object);
    }
}
