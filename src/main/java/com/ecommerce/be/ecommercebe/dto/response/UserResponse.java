package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.UserImp;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import lombok.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponse implements BaseValidate {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullname;
    private Boolean softDelete;
    private Set<UserEntity.UserRole> roles;

    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Map<String, Object> getAttributes() {
        return mapper.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}
