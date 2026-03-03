package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.model.CartEntity;
import com.ecommerce.be.ecommercebe.model.ProductVariantEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemDTORequest implements BaseValidate {
    private Long userId;
    private Long variantId;
    private int quantity;

    @Override
    public Long getId() {
        return null;
    }
}
