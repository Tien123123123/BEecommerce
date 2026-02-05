package com.ecommerce.be.ecommercebe.dto.request;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class ShopDTORequest implements BaseValidate {
    private Long sellerId;
    private String shopName;
    private String shopAddress;

    @Override
    public Long getId() {
        return null;
    }

}
