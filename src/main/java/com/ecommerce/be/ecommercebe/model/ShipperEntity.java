package com.ecommerce.be.ecommercebe.model;

import com.ecommerce.be.ecommercebe.constant.user.UserStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.util.List;

@Entity
@Table(name = "shippers")
@Getter
@Setter
public class ShipperEntity extends BaseAudit implements Persistable<Long> {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference
    private UserEntity userEntity;

    private String vehicleType;
    private String licensePlate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INACTIVE;

    // Shipment
    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShipmentEntity> shipments;

    // Real-time location
    // ? Expand later
    private String activeZone;

    @Transient
    private boolean isNew = true;
    @Override
    public boolean isNew() {
        return this.isNew;
    }
    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

    // Hepler
    public void setUserEntity(UserEntity userEntity){
        this.userEntity = userEntity;
        this.id = (userEntity != null) ? userEntity.getId() : null;
    }
}
