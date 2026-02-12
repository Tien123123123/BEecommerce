package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse implements BaseValidate {
    private Long id;
    private String brandName;
    private String slug;
    private String logoUrl;
    private String description;
    private String country;
}
