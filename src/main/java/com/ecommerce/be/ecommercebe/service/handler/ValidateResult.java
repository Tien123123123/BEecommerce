package com.ecommerce.be.ecommercebe.service.handler;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidateResult<T extends BaseValidate>{
    private boolean status;
    private String message;
    private T object;

    public static <T extends BaseValidate> ValidateResult<T> success(T object){
        return new ValidateResult<>(true, "Pass", object);
    }
    public static <T extends BaseValidate> ValidateResult<T> fail(String message){
        return new ValidateResult<>(false, "Fail", null);
    }
}
