package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Getter
@Setter
public class UserRegisterDTORequest implements BaseValidate {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 40, message = "Username must be between 3 and 40 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "Username can only contain letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullname;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirm_password;

    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 12, message = "Phone number must be between 9 and 12 digits")
    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$",
            message = "Phone number must be a valid Vietnamese mobile number (starts with 03, 05, 07, 08, or 09)")
    private String phone;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Long getId() {
        return null;
    }

}
