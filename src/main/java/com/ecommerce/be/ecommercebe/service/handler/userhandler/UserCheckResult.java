package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.UserImp;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserCheckResult<T extends UserImp> {
    private final String message;
    private final boolean status;
    private final T object;

    public static<T extends UserImp> UserCheckResult<T> success(T object){
        return new UserCheckResult("pass!", true, object);
    }
    public static UserCheckResult<?> fail(String message){
        return new UserCheckResult(message, false, null);
    }
}
