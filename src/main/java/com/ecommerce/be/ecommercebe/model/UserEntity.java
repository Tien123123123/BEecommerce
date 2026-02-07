package com.ecommerce.be.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column
    private String address;
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER) // EAGER -> Instance Load
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING) // String | Ordinal
    @Column
    private Set<UserRole> roles = new HashSet<>(Set.of(UserRole.BUYER));

    // Seller
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private SellerEntity seller;

    // Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    public enum UserRole{
        BUYER, SELLER, ADMIN
    }
}
