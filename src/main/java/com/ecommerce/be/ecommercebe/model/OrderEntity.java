package com.ecommerce.be.ecommercebe.model;

import com.ecommerce.be.ecommercebe.constant.OrderStatus;
import com.ecommerce.be.ecommercebe.dto.request.ShopOrderDTORequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private ShopEntity shop;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "sub_total")
    private BigDecimal subTotal;
    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public void addOrderItem(OrderItemEntity item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}
