package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class CartEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Long sessionId;
    private CartStatus status = CartStatus.ACTIVE;

    public enum CartStatus {
        ACTIVE,
        INACTIVE
    }
}
