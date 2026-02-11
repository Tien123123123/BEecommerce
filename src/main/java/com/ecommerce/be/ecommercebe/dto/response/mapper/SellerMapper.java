package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "shop", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    SellerEntity toEntity(SellerRegisterDTORequest sellerDTO);

    // Entity -> DTO
    @Mapping(target = "user_id", source = "userEntity.id")
    @Mapping(target = "shopName", source = "shop.shopName")
    @Mapping(target = "shopAddress", source = "shop.shopAddress")
    @Mapping(target = "softDelete", ignore = true)
    SellerResponse toDTO(SellerEntity sellerEntity);
}
