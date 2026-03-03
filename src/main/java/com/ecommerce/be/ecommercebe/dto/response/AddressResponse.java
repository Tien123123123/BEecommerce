package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse implements BaseValidate {
    private Long id;
    private Long userId;
    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private String country;
    private String postalCode;
}
