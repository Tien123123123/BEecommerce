package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse implements BaseValidate {
    private Long id;
    private String categoryName;
    private String slug;
    private String description;
    private int sortOrder;
}
