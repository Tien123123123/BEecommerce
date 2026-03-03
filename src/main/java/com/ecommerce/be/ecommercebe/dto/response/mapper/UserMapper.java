package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.AddressResponse;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.model.AddressEntity;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // DTO -> Entity
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    UserEntity toEntity(UserRegisterDTORequest userDTO);

    // Entity -> DTO
    @Mapping(target = "softDelete", ignore = true)
    @Mapping(target = "addresses", source = "addressList", qualifiedByName = "getAddresses")
    UserResponse toDTO(UserEntity userEntity);

    @Named("getAddresses")
    default List<AddressResponse> getAddresses(List<AddressEntity> addressEntityList){
        if (addressEntityList == null){
            return new ArrayList<>();
        }
        return addressEntityList.stream().map(this::addressEntityToResponse)
                .collect(Collectors.toList());
    }

    @Mapping(target = "userId", source = "userEntity.id")
    AddressResponse addressEntityToResponse(AddressEntity entity);
}
