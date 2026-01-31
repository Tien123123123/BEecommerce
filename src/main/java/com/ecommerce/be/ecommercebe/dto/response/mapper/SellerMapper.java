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
    SellerResponse toDTO(SellerEntity sellerEntity);
}
