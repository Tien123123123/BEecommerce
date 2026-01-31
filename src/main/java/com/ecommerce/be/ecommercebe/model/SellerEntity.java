package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
@Getter
@Setter
public class SellerEntity extends BaseEntity{
    @Column
    private String citizenIdentity;
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopEntity> shops = new ArrayList<>();
}
