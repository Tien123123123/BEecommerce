package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.ShopDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ShopResponse;
import com.ecommerce.be.ecommercebe.model.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "orders", ignore = true)
    ShopEntity toEntity(ShopDTORequest shopDTO);

    // Entity -> DTO
    @Mapping(target = "seller_id", source = "seller.userEntity.id")
    ShopResponse toDTO(ShopEntity shopEntity);
}
