package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.CartDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.OrderResponse;
import com.ecommerce.be.ecommercebe.model.CartEntity;
import com.ecommerce.be.ecommercebe.model.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper{
    // Entity -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "shop", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    OrderEntity fromCart(CartEntity entity);

    // Entity -> DTO
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "shopId", source = "shop.id")
    OrderResponse toDTO(OrderEntity entity);
}
