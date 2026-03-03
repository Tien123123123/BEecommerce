package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTORequest implements BaseValidate {
    private Long userId = null;
    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private String country;
    private String postalCode;
    private boolean isDefault;


    @Override
    public Long getId() {
        return null;
    }
}
