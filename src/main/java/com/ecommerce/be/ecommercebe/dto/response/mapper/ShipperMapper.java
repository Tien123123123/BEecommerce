package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.ShipperDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ShipperResponse;
import com.ecommerce.be.ecommercebe.model.ShipperEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipperMapper {
    // DTO -> Entity
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    ShipperEntity toEntity(ShipperDTORequest dto);

    // Entity -> DTO
    @Mapping(target = "userId", source = "entity.id")
    ShipperResponse toDTO(ShipperEntity entity);
}
