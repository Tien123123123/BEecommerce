package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.UserImp;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements UserImp {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullname;
    private Set<UserEntity.UserRole> roles;
}
