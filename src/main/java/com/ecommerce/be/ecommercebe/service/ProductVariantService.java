package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.ProductVariantDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.VariantAttributeDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.AttributeResponse;
import com.ecommerce.be.ecommercebe.dto.response.VariantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantService {
    private final VariantAttributeService variantAttributeService;

    public VariantResponse createVariant(ProductVariantDTORequest request){

        // Variant set up
        VariantResponse variantResponse = VariantResponse.builder()
                .sku(request.getSku())
                .id(request.getId())
                .price(request.getPrice())
                .height(request.getHeight())
                .weight(request.getWeight())
                .costPrice(request.getCostPrice())
                .salePrice(request.getSalePrice())
                .length(request.getLength())
                .build();

        // Variant Attribute
        List<AttributeResponse> attributes = new ArrayList<>();
        for(VariantAttributeDTORequest att : request.getAttributes()){
            AttributeResponse attribute = variantAttributeService.createAttribute(att);
            attributes.add(attribute);
        }
        variantResponse.setAttributes(attributes);

        return variantResponse;
    }
}
