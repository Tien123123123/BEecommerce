package com.ecommerce.be.ecommercebe.service.handler.userhandler;

import com.ecommerce.be.ecommercebe.dto.UserImp;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public abstract class UserHandler<T> {
    private UserHandler<? super T> next;

    public UserCheckResult<?> handle(T object){
        UserCheckResult<?> result = checkValid(object);
        if(!result.isStatus()){
            return result;
        }
        return next != null ? next.handle(object) : result;
    }

    protected abstract UserCheckResult<?> checkValid(T object);

    public UserHandler<T> setNext(UserHandler<? super T> next) {
        this.next = next;
        return this;
    }
}
