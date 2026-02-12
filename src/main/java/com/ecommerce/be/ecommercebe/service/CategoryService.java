package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.CategoryDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.CategoryResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.CategoryMapper;
import com.ecommerce.be.ecommercebe.model.CategoryEntity;
import com.ecommerce.be.ecommercebe.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @CachePut(value = "categories", key = "#result.id")
    public CategoryResponse createCategory(CategoryDTORequest dto){
        CategoryEntity entity = categoryMapper.toEntity(dto);
        CategoryEntity saved = categoryRepository.save(entity);

        CategoryResponse response = categoryMapper.toDTO(saved);

        return response;
    }

    @Cacheable(value = "categories", key = "#id")
    public CategoryEntity getCategory(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid category by id " + id));
    }
    public CategoryResponse getCategoryDetail(Long id){
        CategoryEntity entity = getCategory(id);

        return categoryMapper.toDTO(entity);
    }
    @CacheEvict(value = "categories", key = "#id")
    public void deleteCategory(Long id){
        CategoryEntity entity = getCategory(id);
        categoryRepository.deleteById(entity.getId());
    }
    @CachePut(value = "categories", key = "#result.id")
    public CategoryResponse restoreCategory(Long id){
        Boolean isDeleted = categoryRepository.isSoftDeleted(id);

        if(Boolean.FALSE.equals(isDeleted)){
            throw new RuntimeException("Category " + id + " is already deleted!");
        }
        CategoryEntity entity = categoryRepository.getCategoryIncludeDeleted(id)
                .orElseThrow(()->new RuntimeException("Invalid category by id: " + id));

        categoryRepository.restoreById(entity.getId());

        return getCategoryDetail(id);
    }
}
