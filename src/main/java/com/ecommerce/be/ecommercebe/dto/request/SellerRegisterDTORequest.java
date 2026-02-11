package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SellerRegisterDTORequest implements BaseValidate {
    @NotBlank(message = "Citizen ID / ID Number is required")
    @Size(min = 9, max = 12, message = "Citizen ID must be 9 or 12 digits long")
    @Pattern(regexp = "^\\d{9}$|^\\d{12}$",
            message = "Citizen ID must contain exactly 9 or 12 digits (0-9 only)")
    private String citizenIdentity;
    private Long id = null;
}
