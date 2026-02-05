package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;

public class ValidateSellerStatus extends Handler<SellerResponse> {

    @Override
    protected ValidateResult<SellerResponse> doValidate(SellerResponse object) {
        if(object.getSoftDelete()){
            return ValidateResult.fail("Seller is deleted!");
        }

        return ValidateResult.success(object);
    }
}
