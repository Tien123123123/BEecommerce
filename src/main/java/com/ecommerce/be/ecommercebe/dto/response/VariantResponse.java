package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariantResponse implements BaseValidate {

    private Long id;
    private String sku;
    private BigDecimal price;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal height;
    private Integer stockQuantity;
    private ProductVariantStatus status;
    private List<String> imageUrls = new ArrayList<>();

    // Attribute
    private List<AttributeResponse> attributes = new ArrayList<>();

    public enum ProductVariantStatus {
        ACTIVE,
        INACTIVE
    }
}