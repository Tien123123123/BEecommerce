package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.ProductDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ProductResponse;
import com.ecommerce.be.ecommercebe.model.CategoryEntity;
import com.ecommerce.be.ecommercebe.model.ProductEntity;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // DTO to Entity
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "shop", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "categories", ignore = true)
    ProductEntity toEntity(ProductDTORequest dto);

    // Entity to DTO
    @Mapping(target = "shopId", source = "shop.id")
    @Mapping(target = "brandId", source = "brand.id", ignore = true)
    @Mapping(target = "categoryIds", qualifiedByName = "getCategoryId", ignore = true)
    ProductResponse toDTO(ProductEntity entity);

    @Name("getCategoryId")
    default Set<Long> getCategoryId(Set<CategoryEntity> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream().map(CategoryEntity::getId)
                .collect(Collectors.toSet());
    }
}
