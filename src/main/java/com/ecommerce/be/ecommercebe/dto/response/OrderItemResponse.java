package com.ecommerce.be.ecommercebe.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Long variantId;

    private String productName;
    private String sku;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
}
