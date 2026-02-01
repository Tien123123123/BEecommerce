package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;

public class ValidateSellerStatus extends Handler<UserResponse> {
    @Override
    protected ValidateResult<UserResponse> doValidate(UserResponse object) {
        return ValidateResult.success(object);
    }
}
