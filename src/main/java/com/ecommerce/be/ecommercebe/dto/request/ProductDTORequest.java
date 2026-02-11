package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;

import java.util.List;

public class ProductDTORequest implements BaseValidate {
    private String productName;
    private String slug;
    private String description;
    private List<String> categories;

    @Override
    public Long getId() {
        return null;
    }
}
