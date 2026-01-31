package com.ecommerce.be.ecommercebe.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class ResponseData<T> {
    private final String message;
    private final int status;
    private final T object;
}
