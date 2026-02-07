package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.ProductDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ProductResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ProductMapper;
import com.ecommerce.be.ecommercebe.model.ProductEntity;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import com.ecommerce.be.ecommercebe.model.ShopEntity;
import com.ecommerce.be.ecommercebe.repository.ProductRepository;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerService sellerService;
    private final ShopService shopService;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(Long seller_id, Long shop_id, ProductDTORequest productDTORequest){
        SellerEntity sellerEntity = sellerService.getSeller(seller_id);
        ShopEntity shopEntity = shopService.getShop(sellerEntity.getUserEntity().getId(), shop_id);

        ProductEntity productEntity = productMapper.toEntity(productDTORequest);


        return productMapper.toDTO(productEntity);
    }
}
