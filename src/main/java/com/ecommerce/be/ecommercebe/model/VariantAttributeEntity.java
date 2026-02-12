package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "variant_attributes")
@Getter
@Setter
public class VariantAttributeEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ProductVariantEntity variant;

    private String name;
    private String value;
}
