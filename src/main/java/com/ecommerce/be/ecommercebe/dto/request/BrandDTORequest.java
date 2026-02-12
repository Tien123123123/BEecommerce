package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandDTORequest implements BaseValidate {
    private String brandName;
    private String slug;
    private String logoUrl;
    private String description;
    private String country;

    @Override
    public Long getId() {
        return null;
    }
}
