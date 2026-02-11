package com.ecommerce.be.ecommercebe.exception;

import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseData<Object>> handleRuntimeException(RuntimeException ex) {
        ResponseData<Object> response = new ResponseData<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
