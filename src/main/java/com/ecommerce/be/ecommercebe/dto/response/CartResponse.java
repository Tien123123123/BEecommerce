package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.model.CartEntity;
import com.ecommerce.be.ecommercebe.model.CartItemEntity;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartResponse implements BaseValidate{
    private Long id;
    private Long userId;
    private List<CartItemResponse> cartItems = new ArrayList<>();
    private Long sessionId;
    private CartStatus status;

    public enum CartStatus {
        ACTIVE,
        INACTIVE
    }

}
