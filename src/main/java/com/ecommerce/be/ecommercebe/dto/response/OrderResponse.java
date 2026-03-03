package com.ecommerce.be.ecommercebe.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long userId;
    private Long shopId;

    private OrderStatus status;

    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;

    private List<OrderItemResponse> orderItems = new ArrayList<>();

    public enum OrderStatus{
        PENDING,
        APPROVE,
        DELIVERY,
        COMPLETE
    }
}
