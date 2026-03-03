package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.constant.ShipmentStatus;
import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentResponse implements BaseValidate {
    private OrderResponse order;
    private ShipmentStatus status;
    private String trackingNumber;

    private Long shipperId;

    // Delivery time
    private LocalDateTime expectedTime;
    private LocalDateTime pickedUpTime;
    private LocalDateTime deliveryAt;

    // Proof
    private String proofImageUrl;
    private String failedReason;
    private String shipmentNote;

    @Override
    public Long getId() {
        return 0L;
    }
}
