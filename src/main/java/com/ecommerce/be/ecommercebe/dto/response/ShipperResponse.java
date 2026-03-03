package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.constant.user.UserStatus;
import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipperResponse implements BaseValidate {
    private Long id;
    private Long userId;

    private String vehicleType;
    private String licensePlate;

    private UserStatus status;
    private List<ShipmentResponse> shipments = new ArrayList<>();

    private String activeZone;
}
