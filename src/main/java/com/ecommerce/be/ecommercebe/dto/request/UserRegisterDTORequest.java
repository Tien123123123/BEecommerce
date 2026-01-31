package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.UserImp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTORequest implements BaseValidate {
    private String username;
    private String email;
    private String fullname;
    private String password;
    private String confirm_password;
    private int phone;

    @Override
    public Long getId() {
        return 0L;
    }
}
