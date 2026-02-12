package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.ProductDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.ProductVariantDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.VariantAttributeDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ProductResponse;
import com.ecommerce.be.ecommercebe.dto.response.VariantResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.AttributeMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ProductMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.VariantMapper;
import com.ecommerce.be.ecommercebe.model.ProductEntity;
import com.ecommerce.be.ecommercebe.model.ProductVariantEntity;
import com.ecommerce.be.ecommercebe.model.VariantAttributeEntity;
import com.ecommerce.be.ecommercebe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductVariantService productVariantService;

    private final BrandService brandService;
    private final ShopService shopService;

    private final ProductMapper productMapper;
    private final VariantMapper variantMapper;
    private final AttributeMapper attributeMapper;

    public ProductResponse createProduct(ProductDTORequest request) {
        ProductEntity productEntity = productMapper.toEntity(request);
        productEntity.setBrand(brandService.getBrand(request.getBrandId()));
        productEntity.setShop(shopService.getShop(request.getShopId()));
        productEntity.setStatus(ProductEntity.ProductStatus.ACTIVE);

        // Handler
        if (request.getVariants() == null) {
            throw new RuntimeException("Product must have at least 1 variant product!");
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
        ProductResponse productResponse = productMapper.toDTO(productEntity);

        return productResponse;
    }
}
