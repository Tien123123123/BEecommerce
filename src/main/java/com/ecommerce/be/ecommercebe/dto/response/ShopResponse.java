package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ShopResponse implements BaseValidate {
    private Long seller_id;
    private String shopName;
    private String shopAddress;

    @Override
    public Long getId() {
        return this.seller_id;
    }
}
