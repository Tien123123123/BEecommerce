package com.ecommerce.be.ecommercebe.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductResponse {
    private String productName;
    private String slug;
    private String description;

    // Relationships
    private Set<String> categories;
    private String brandId;
    private String brandName;
    private String shopId;
    private String status;
}
