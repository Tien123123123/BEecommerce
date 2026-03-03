package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponse implements BaseValidate {
    private Long id;
    private Long cartId;
    private Long variantId;
    private int quantity;
    private BigDecimal priceAtAdd;
}
