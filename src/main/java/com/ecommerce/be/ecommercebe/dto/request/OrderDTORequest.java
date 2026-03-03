package com.ecommerce.be.ecommercebe.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDTORequest {
    private Long userId;
    private String voucherCode;
    private List<Long> cartItemIds;
}
