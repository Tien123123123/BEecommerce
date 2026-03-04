package com.ecommerce.be.ecommercebe.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTORequest{
    private String userMail;
    private String password;
}
