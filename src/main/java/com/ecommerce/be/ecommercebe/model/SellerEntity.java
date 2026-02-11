package com.ecommerce.be.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "sellers")
@Getter
@Setter
public class SellerEntity extends BaseAudit implements Persistable<Long> {
    @Id
    private Long id;
    @Column
    private String citizenIdentity;
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference // Jackson not allow to serialize this field
    private UserEntity userEntity;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ShopEntity shop;

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.id = (userEntity != null) ? userEntity.getId() : null;
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
