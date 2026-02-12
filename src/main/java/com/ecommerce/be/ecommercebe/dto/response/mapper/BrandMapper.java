package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.BrandDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.BrandResponse;
import com.ecommerce.be.ecommercebe.model.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    BrandEntity toEntity(BrandDTORequest dto);

    // Entity -> DTO
    BrandResponse toDTO(BrandEntity entity);
}
