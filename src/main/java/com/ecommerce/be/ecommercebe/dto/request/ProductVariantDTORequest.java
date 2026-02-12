package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTORequest implements BaseValidate {
    private String sku;
    private BigDecimal price;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal height;

    @Min(value = 0, message = "Quantity must >= 0")
    private Integer stockQuantity = 0;

    @NotEmpty(message = "Variant Attribute must not be empty")
    private List<VariantAttributeDTORequest> attributes = new ArrayList<>();

    @Override
    public Long getId() {
        return 0L;
    }
}
