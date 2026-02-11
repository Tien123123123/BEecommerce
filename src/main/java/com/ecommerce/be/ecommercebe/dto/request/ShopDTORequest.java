package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ShopDTORequest implements BaseValidate {
    private Long sellerId=null;
    @NotBlank(message = "Shop name is required")
    @Size(min = 3, max = 100, message = "Shop name must be between 3 and 100 characters")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s\\-&'.,()]+$",
            message = "Shop name can only contain letters, numbers, spaces, and basic symbols (- & ' . , ())")
    private String shopName;

    @NotBlank(message = "Shop address is required")
    @Size(min = 10, max = 255, message = "Shop address must be between 10 and 255 characters")
    private String shopAddress;

    @Override
    public Long getId() {
        return null;
    }

}
