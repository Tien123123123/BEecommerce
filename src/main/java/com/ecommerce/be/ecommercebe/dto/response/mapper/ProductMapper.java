package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.ProductDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ProductResponse;
import com.ecommerce.be.ecommercebe.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // DTO -> Entity
    ProductEntity toEntity(ProductDTORequest productDTORequest);

    // Entity -> DTO
    @Mapping(target = "brandId", source="productEntity.brand.id")
    @Mapping(target = "brandName", source = "productEntity.brand.brandName")
    @Mapping(target = "shopId", source = "productEntity.shop.id")
    @Mapping(target = "status", expression = "java(String.valueOf(productEntity.status))")
    @Mapping(target = "categories", expression = "java(productEntity.getCategories() != null : null ?" +
            "productEntity.getCategories().stream().map(c->c.getCategoriesName().collect(java.util.stream.Collectors.toList())))")
    ProductResponse toDTO(ProductEntity productEntity);
}
