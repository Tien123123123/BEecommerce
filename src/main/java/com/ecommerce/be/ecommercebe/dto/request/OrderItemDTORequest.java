package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTORequest implements BaseValidate {
    private Long variantId;
    private int quantity;
    private String productName;
    private String sku;

    private BigDecimal unitPrice;
    private BigDecimal subTotal;

    @Override
    public Long getId() {
        return null;
    }
}
