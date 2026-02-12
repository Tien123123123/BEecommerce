package com.ecommerce.be.ecommercebe.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String productName;
    private String slug;
    private String description;
    private Long shopId;
    private Long brandId;
    private Set<Long> categoryIds;
    private List<String> imageUrls = new ArrayList<>();
    private boolean featured;
    private ProductStatus status;

    // List variants (nested)
    private List<VariantResponse> variants = new ArrayList<>();

    public enum ProductStatus {
        ACTIVE,
        INACTIVE
    }
}
