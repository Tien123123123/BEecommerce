package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.ProductDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ProductResponse;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ProductMapper;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import com.ecommerce.be.ecommercebe.model.ShopEntity;
import com.ecommerce.be.ecommercebe.service.ProductService;
import com.ecommerce.be.ecommercebe.service.SellerService;
import com.ecommerce.be.ecommercebe.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{seller_id}/product")
@RequiredArgsConstructor
public class ProductController {
    private final SellerService sellerService;
    private final ShopService shopService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping("/{id}")
    public ResponseData<ProductResponse> createProduct(@PathVariable Long seller_id, @PathVariable Long id, @RequestBody ProductDTORequest productDTO){
        SellerEntity SellerEntity = sellerService.getSeller(seller_id);
        ShopEntity shopEntity = shopService.


        ProductResponse product = productService.createProduct(seller_id, id, productDTO);


        return new ResponseData<>("Product ", HttpStatus.CREATED.value(), product);
    }
}
