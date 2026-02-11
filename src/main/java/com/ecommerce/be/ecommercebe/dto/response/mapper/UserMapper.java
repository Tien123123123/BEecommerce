package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // DTO -> Entity
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "carts", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    UserEntity toEntity(UserRegisterDTORequest userDTO);

    // Entity -> DTO
    @Mapping(target = "softDelete", ignore = true)
    UserResponse toDTO(UserEntity userEntity);
}
