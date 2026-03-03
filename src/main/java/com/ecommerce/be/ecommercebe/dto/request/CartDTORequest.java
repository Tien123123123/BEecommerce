package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDTORequest implements BaseValidate {
    private Long userId;
    private List<CartItemDTORequest> cartItems = new ArrayList<>();
    private Long sessionId;

    @Override
    public Long getId() {
        return null;
    }
}
