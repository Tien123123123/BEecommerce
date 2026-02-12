package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantAttributeDTORequest implements BaseValidate {
    private String name;
    private String value;

    @Override
    public Long getId() {
        return 0L;
    }
}
