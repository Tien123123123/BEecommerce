package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.constant.OrderStatus;
import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopOrderDTORequest implements BaseValidate {
    private Long orderId;
    private Long shopId;
    private OrderStatus status;

    @Override
    public Long getId() {
        return 0L;
    }
}
