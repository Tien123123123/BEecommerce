package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.CartItemDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.OrderItemResponse;
import com.ecommerce.be.ecommercebe.model.CartItemEntity;
import com.ecommerce.be.ecommercebe.model.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    // Entity -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "variant", ignore = true)
    OrderItemEntity toEntity(CartItemEntity entity);

    // Entity -> DTO
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "variantId", source = "variant.id")
    OrderItemResponse toDTO(OrderItemEntity entity);
}
