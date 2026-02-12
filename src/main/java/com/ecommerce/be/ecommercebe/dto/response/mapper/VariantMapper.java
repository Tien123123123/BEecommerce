package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.ProductVariantDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.VariantResponse;
import com.ecommerce.be.ecommercebe.model.ProductVariantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    // DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "attributes", ignore = true)
    ProductVariantEntity toEntity(ProductVariantDTORequest dto);

    // Entity to DTO
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "entity.attributes", ignore = false)
    @Mapping(target = "salePrice", source = "salePrice")
    @Mapping(target = "costPrice", source = "costPrice")
    @Mapping(target = "stockQuantity", source = "stockQuantity")
    VariantResponse toDTO(ProductVariantEntity entity);

}
