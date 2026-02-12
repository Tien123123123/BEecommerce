package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.VariantAttributeDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.AttributeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VariantAttributeService {

    public AttributeResponse createAttribute(VariantAttributeDTORequest request){
        // Attributes set up
        AttributeResponse attributeResponse = AttributeResponse.builder()
                .name(request.getName())
                .value(request.getValue())
                .build();

        return attributeResponse;
    }
}
