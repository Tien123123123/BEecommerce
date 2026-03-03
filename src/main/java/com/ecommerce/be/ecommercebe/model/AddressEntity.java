package com.ecommerce.be.ecommercebe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class AddressEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private String addressLine;
    private String ward;
    private String district;
    private String city;
    private String country;
    private String postalCode;
    private boolean isDefault = false; // default address ?
}
