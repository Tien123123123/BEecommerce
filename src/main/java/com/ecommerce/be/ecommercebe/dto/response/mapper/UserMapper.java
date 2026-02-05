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
    @Mapping(target = "seller", ignore = true)
    UserEntity toEntity(UserRegisterDTORequest userDTO);


    UserEntity toEntity(UserResponse userDTO);
    // Entity -> DTO
    @Mapping(target = "id", source = "id")
    UserResponse toDTO(UserEntity userEntity);
}
