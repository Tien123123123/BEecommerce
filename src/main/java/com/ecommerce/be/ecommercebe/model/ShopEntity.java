package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "shops")
@Getter
@Setter
public class ShopEntity extends BaseAudit implements Persistable<Long> {
    @Id
    private Long id;
    @Column
    private String shopName;
    @Column
    private String shopAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerEntity seller;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
        this.id = (seller != null) ? seller.getId() : null;
    }

    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }
}
