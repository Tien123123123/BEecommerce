package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.CartDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.CartItemDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.CartItemResponse;
import com.ecommerce.be.ecommercebe.dto.response.CartResponse;
import com.ecommerce.be.ecommercebe.model.CartEntity;
import com.ecommerce.be.ecommercebe.model.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "variant", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "priceAtAdd", ignore = true)
    CartItemEntity toEntity(CartItemDTORequest dto);

    // Entity -> DTO
    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "variantId", source = "variant.id")
    CartItemResponse toDTO(CartItemEntity entity);
}
