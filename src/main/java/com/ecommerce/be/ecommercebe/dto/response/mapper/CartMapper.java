package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.CartDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.CartItemResponse;
import com.ecommerce.be.ecommercebe.dto.response.CartResponse;
import com.ecommerce.be.ecommercebe.model.CartEntity;
import com.ecommerce.be.ecommercebe.model.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "status", ignore = true)
    CartEntity toEntity(CartDTORequest dto);

    // Entity -> DTO
    @Mapping(target = "userId", source = "user.id")
    CartResponse toDTO(CartEntity entity);
}
