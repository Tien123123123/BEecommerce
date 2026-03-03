package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.CategoryDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.ProductDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.ProductVariantDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.VariantAttributeDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ProductResponse;
import com.ecommerce.be.ecommercebe.dto.response.VariantResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.AttributeMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ProductMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.VariantMapper;
import com.ecommerce.be.ecommercebe.model.CategoryEntity;
import com.ecommerce.be.ecommercebe.model.ProductEntity;
import com.ecommerce.be.ecommercebe.model.ProductVariantEntity;
import com.ecommerce.be.ecommercebe.model.VariantAttributeEntity;
import com.ecommerce.be.ecommercebe.repository.ProductRepository;
import com.ecommerce.be.ecommercebe.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductVariantService productVariantService;

    private final BrandService brandService;
    private final ShopService shopService;
    private final CategoryService categoryService;

    private final ProductMapper productMapper;
    private final VariantMapper variantMapper;
    private final AttributeMapper attributeMapper;
    private final ProductVariantRepository productVariantRepository;

    @CachePut(value = "products", key = "#result.id")
    public ProductResponse createProduct(ProductDTORequest request) {
        ProductEntity productEntity = productMapper.toEntity(request);
        productEntity.setBrand(brandService.getBrand(request.getBrandId()));
        productEntity.setShop(shopService.getShop(request.getShopId()));
        productEntity.setProductImages(new ArrayList<>());
        productEntity.setStatus(ProductEntity.ProductStatus.ACTIVE);

        // Handler
        if (request.getVariants() == null) {
            throw new RuntimeException("Product must have at least 1 variant product!");
        }
        // ? Category
        productEntity.setCategories(new HashSet<>());
        for (Long id : request.getCategories()){
            CategoryEntity categoryEntity = categoryService.getCategory(id);
            productEntity.addCategory(categoryEntity);
        }

        // ? Variant process

        productEntity.setVariants(new ArrayList<>());
        for (ProductVariantDTORequest variantDTO : request.getVariants()) {
            ProductVariantEntity variantEntity = variantMapper.toEntity(variantDTO);

            // ? Attribute process
            for(VariantAttributeDTORequest attributeDTO : variantDTO.getAttributes()){
                VariantAttributeEntity attributeEntity = attributeMapper.toEntity(attributeDTO);
                variantEntity.addAttribute(attributeEntity);
            }

            productEntity.addVariant(variantEntity);
        }

        // ? Save and Response data
        ProductEntity saved = productRepository.save(productEntity);
        ProductResponse productResponse = productMapper.toDTO(saved);

        return productResponse;
    }

    protected ProductEntity getProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cannot find product by id " + id));
    }
    @Cacheable(value = "products", key = "id")
    public ProductResponse getProductDetail(Long id){
        return productMapper.toDTO(getProduct(id));
    }

    protected ProductVariantEntity getVariant(Long id){
        return productVariantRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cannot find product variant by id " + id));
    }
    @Cacheable(value = "variants", key = "id")
    public VariantResponse getVariantDetail(Long id){
        return variantMapper.toDTO(getVariant(id));
    }

}
