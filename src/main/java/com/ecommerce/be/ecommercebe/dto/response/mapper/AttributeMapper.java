package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.VariantAttributeDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.AttributeResponse;
import com.ecommerce.be.ecommercebe.dto.response.VariantResponse;
import com.ecommerce.be.ecommercebe.model.VariantAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    VariantAttributeEntity toEntity(VariantAttributeDTORequest dto);

    // Entity -> DTO
    @Mapping(target = "id", source = "id", ignore = true)
    AttributeResponse toDTO(VariantAttributeEntity entity);

}
