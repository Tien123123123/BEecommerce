package com.ecommerce.be.ecommercebe.model;

import com.ecommerce.be.ecommercebe.constant.user.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends BaseEntity{
    @Column(name = "user_name")
    private String username;
    @Column
    private String email;
    @Column(name = "full_name")
    private String fullname;
    @Column
    private String password;
    @Column
    private String phone;
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.LAZY) // EAGER -> Instance Load
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING) // String | Ordinal
    @Column
    private Set<UserRole> roles = new HashSet<>(Set.of(UserRole.BUYER));
    // Address
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AddressEntity> addressList = new ArrayList<>();

    // Seller
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private SellerEntity seller;
    // Shipper
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private ShipperEntity shipper;

    // Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();
    // Cart
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private CartEntity cart;

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
        if (seller != null) {
            seller.setUserEntity(this);
        }
    }
    public void addAddress(AddressEntity entity){
        if(this.addressList == null){
            this.addressList = new ArrayList<>();
        }
        addressList.add(entity);
        entity.setUserEntity(this);
    }

    // Helper
    public void promoteToShipper(ShipperEntity shipper){
        this.shipper = shipper;
        shipper.setUserEntity(this);
        this.roles.add(UserRole.SHIPPER);
    }
}
