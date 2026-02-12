package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTORequest implements BaseValidate {
    private String categoryName;
    private String slug;
    private String description;
    private int sortOrder;

    @Override
    public Long getId() {
        return null;
    }
}
