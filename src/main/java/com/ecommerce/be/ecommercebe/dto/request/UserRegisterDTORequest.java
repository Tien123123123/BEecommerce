package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Getter
@Setter
public class UserRegisterDTORequest implements BaseValidate {
    private String username;
    private String email;
    private String fullname;
    private String password;
    private String confirm_password;
    private String phone;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return mapper.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}
