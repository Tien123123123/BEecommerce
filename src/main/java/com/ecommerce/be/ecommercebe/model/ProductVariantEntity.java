package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
public class ProductVariantEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> productImages = new ArrayList<>();
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems = new ArrayList<>();
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantAttributeEntity> attributes = new ArrayList<>();

    private String sku;
    private BigDecimal price;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal height;
    private Long stockQuantity;

    private ProductVariantStatus status = ProductVariantStatus.INACTIVE;

    // * Product variant status
    public enum ProductVariantStatus {
        ACTIVE,
        INACTIVE
    }

    // * Helper
    public void addAttribute(VariantAttributeEntity entity) {
        if (attributes == null) {
            this.attributes = new ArrayList<>();
        }
        attributes.add(entity);
        entity.setVariant(this);
    }

}
