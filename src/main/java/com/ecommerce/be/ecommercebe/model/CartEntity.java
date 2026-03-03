package com.ecommerce.be.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class CartEntity extends BaseAudit implements Persistable<Long> {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @MapsId
    @JsonBackReference
    private UserEntity user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItems = new ArrayList<>();

    private Long sessionId;
    private CartStatus status = CartStatus.ACTIVE;

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

    public enum CartStatus {
        ACTIVE,
        INACTIVE
    }

    // Helper
    public void addCartItems(CartItemEntity entity){
        cartItems.add(entity);
        entity.setCart(this);
    }
}
