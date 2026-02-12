package com.ecommerce.be.ecommercebe.dto.response.mapper;

import com.ecommerce.be.ecommercebe.dto.request.CategoryDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.CategoryResponse;
import com.ecommerce.be.ecommercebe.model.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    // DTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    CategoryEntity toEntity(CategoryDTORequest dto);

    // Entity -> DTO
    CategoryResponse toDTO(CategoryEntity entity);
}
