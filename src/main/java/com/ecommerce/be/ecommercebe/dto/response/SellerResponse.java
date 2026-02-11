package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SellerResponse implements BaseValidate {
    private Long user_id;
    private String citizenIdentity;
    private Boolean softDelete;
    private String shopName;
    private String shopAddress;

    @Override
    public Long getId() {
        return user_id;
    }

}
