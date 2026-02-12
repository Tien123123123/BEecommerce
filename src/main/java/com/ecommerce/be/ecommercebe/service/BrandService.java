package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.BrandDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.BrandResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.BrandMapper;
import com.ecommerce.be.ecommercebe.model.BrandEntity;
import com.ecommerce.be.ecommercebe.repository.BrandRepository;
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
public class BrandService {
    private final Logger logger = LoggerFactory.getLogger(BrandService.class);

    private final BrandMapper brandMapper;
    private final BrandRepository brandRepository;

    @CachePut(value = "brands", key = "#result.id")
    public BrandResponse createBrand(BrandDTORequest dto){
        BrandEntity brandEntity = brandMapper.toEntity(dto);
        BrandEntity saved = brandRepository.save(brandEntity);

        BrandResponse brandResponse = brandMapper.toDTO(saved);

        return brandResponse;
    }

    public BrandEntity getBrand(Long id){
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid brand by id: " + id));
    }

    @Cacheable(value = "brands", key = "#id")
    public BrandResponse getBrandDetail(Long id){
        BrandEntity entity = getBrand(id);
        return brandMapper.toDTO(entity);
    }

    @CacheEvict(value = "brands", key = "#id")
    public void deleteBrand(Long id){
        BrandEntity entity = getBrand(id);
        brandRepository.deleteById(id);
    }

    @CachePut(value = "brands", key = "#result.id")
    public BrandResponse restoreBrand(Long id){
        boolean isDeleted = brandRepository.isSoftDeleted(id);
        if(Boolean.FALSE.equals(isDeleted)){
            throw new RuntimeException("Brand " + id + " is not deleted!");
        }

        BrandEntity entity = brandRepository.getBrandIncludeDeleted(id)
                .orElseThrow(()->new RuntimeException("Invalid brand by id: " + id));

        brandRepository.restoreById(entity.getId());

        return getBrandDetail(id);
    }
}
