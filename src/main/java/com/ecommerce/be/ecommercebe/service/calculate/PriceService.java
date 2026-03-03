package com.ecommerce.be.ecommercebe.service.calculate;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceService {
    private static final int SCALE = 0; // VND: 0 - USD: 2

    public BigDecimal calculateShippingFee(Long shopId, Long userId){
        return new BigDecimal("2000");
    }
    public BigDecimal calculateDiscount(String voucherCode, BigDecimal subTotals){
        if(voucherCode != null){
            return subTotals.multiply(new BigDecimal("0.5")).setScale(SCALE, RoundingMode.HALF_UP);
        }
        return new BigDecimal("0");
    }
    public BigDecimal calculateTotalAmount(BigDecimal subTotals, BigDecimal discount, BigDecimal shippingFee){
        BigDecimal total = subTotals.subtract(discount).add(shippingFee);

        return total.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : total.setScale(SCALE, RoundingMode.HALF_UP);
    }

}
