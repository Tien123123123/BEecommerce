package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shops")
public class ShopEntity extends BaseEntity{
    @Column
    private String shopName;
    @Column String shopAddress;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerEntity seller;
}
