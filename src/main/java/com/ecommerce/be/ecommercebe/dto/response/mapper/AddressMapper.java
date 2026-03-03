package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.AddressDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.AddressResponse;
import com.ecommerce.be.ecommercebe.model.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    // DTO -> Entity
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    AddressEntity toEntity(AddressDTORequest dto);

    // Entity -> DTO
    @Mapping(target = "userId", source = "entity.userEntity.id")
    AddressResponse toDTO(AddressEntity entity);
}
