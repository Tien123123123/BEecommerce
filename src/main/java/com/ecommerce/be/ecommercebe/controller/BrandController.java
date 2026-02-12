package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.BrandDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.BrandResponse;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.service.BrandService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    private final Logger logger = LoggerFactory.getLogger(BrandController.class);

    private final BrandService brandService;

    @PostMapping
    public ResponseData<BrandResponse> createBrand(@Valid @RequestBody BrandDTORequest request){
        logger.info("[BRAND_CONTROLLER][createBrand] Create Brand {}", request.getBrandName());
        BrandResponse response = brandService.createBrand(request);

        return new ResponseData<>("Brand " + response.getBrandName() + " is created", HttpStatus.CREATED.value(), response);
    }

    @GetMapping("/{id}")
    public ResponseData<BrandResponse> getBrandDetail(@PathVariable Long id){
        logger.info("[BRAND_CONTROLLER][getBrand] Get Brand {}", id);
        BrandResponse response = brandService.getBrandDetail(id);

        return new ResponseData<>("Get Brand " + response.getBrandName() + " successfully!", HttpStatus.ACCEPTED.value(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseData<BrandResponse> deleteBrand(@PathVariable Long id){
        logger.info("[BRAND_CONTROLLER][deleteBrand] Delete Brand {}", id);
        brandService.deleteBrand(id);

        return new ResponseData<>("Delete Brand " + id + " successfully!", HttpStatus.ACCEPTED.value(), null);
    }

    @PatchMapping("/{id}")
    public ResponseData<BrandResponse> restoreBrand(@PathVariable Long id){
        logger.info("[BRAND_CONTROLLER][restoreBrand] Restore Brand {}", id);
        BrandResponse response = brandService.restoreBrand(id);

        return new ResponseData<>("Restore Brand " + response.getBrandName() + " successfully!", HttpStatus.ACCEPTED.value(), response);
    }
}
