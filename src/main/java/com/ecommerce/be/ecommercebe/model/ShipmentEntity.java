package com.ecommerce.be.ecommercebe.model;

import com.ecommerce.be.ecommercebe.constant.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shipments")
@Getter
@Setter
public class ShipmentEntity extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private ShipperEntity shipper;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status = ShipmentStatus.PENDING;

    // Delivery time
    private LocalDateTime expectedTime;
    private LocalDateTime pickedUpTime;
    private LocalDateTime deliveryAt;

    // Proof
    private String proofImageUrl;
    private String failedReason;
    private String shipmentNote;

    @PrePersist
    public void generateTrackingNumber() {
        if (this.trackingNumber == null) {
            this.trackingNumber = "SHIP-" + System.currentTimeMillis();
        }
    }

}
