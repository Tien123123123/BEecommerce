package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
public class BrandEntity extends BaseEntity{
    @Column(name = "brand_name")
    private String brandName;
    private String slug; // Beautiful brand url
    @Column(name = "logo_url")
    private String logoUrl;
    private String description;
    private String country;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();
}
