package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipperDTORequest implements BaseValidate {
    private Long userId;

    private String vehicleType;
    private String licensePlate;

    @Override
    public Long getId() {
        return 0L;
    }
}
