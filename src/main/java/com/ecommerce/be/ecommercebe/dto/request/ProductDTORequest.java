package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProductDTORequest implements BaseValidate {
    private String productName;
    private String slug;
    private String description;

    private Long shopId;
    private Long brandId;

    // List
    private Set<Long> categories;
    private List<String> imageUrls;
    private Boolean featured = false;
    private List<ProductVariantDTORequest> variants = new ArrayList<>();


    @Override
    public Long getId() {
        return null;
    }
}
