package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    // DTO -> Entity
    SellerEntity toEntity(SellerRegisterDTORequest sellerDTO);

    // Entity -> DTO
    @Mapping(target = "user_id", source = "userEntity.id")
    @Mapping(target = "shops", expression = "java(sellerEntity.getShops() != null ? " +
            "sellerEntity.getShops().stream().map(s -> s.getShopName()).collect(java.util.stream.Collectors.toList()) : null)")
    SellerResponse toDTO(SellerEntity sellerEntity);
}
