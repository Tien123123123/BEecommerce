package com.ecommerce.be.ecommercebe.service.handler;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Handler<T extends BaseValidate> {
    private Handler<T> next;

    public ValidateResult<T> validate(T object){
        ValidateResult<T> result = doValidate(object);
        if(!result.isStatus()){
            return result;
        }

        return next != null ? next.validate(object) : result;
    }
    protected abstract ValidateResult<T> doValidate(T object);
}
