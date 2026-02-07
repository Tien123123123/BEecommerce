package com.ecommerce.be.ecommercebe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopEntity shop;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "sub_total")
    private Long subTotal;
    @Column(name = "shipping_fee")
    private Long shippingFee;
    @Column(name = "discount_amount")
    private Long discountAmount;
    @Column(name = "total_amount")
    private Long totalAmount;

    public enum OrderStatus{
        PENDING,
        APPROVE,
        DELIVERY,
        COMPLETE
    }
}
