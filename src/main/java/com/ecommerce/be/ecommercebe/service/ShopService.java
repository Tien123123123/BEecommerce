package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.ShopDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.dto.response.ShopResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.SellerMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ShopMapper;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import com.ecommerce.be.ecommercebe.model.ShopEntity;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.SellerRepository;
import com.ecommerce.be.ecommercebe.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final Logger logger = LoggerFactory.getLogger(SellerService.class);
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final UserService userService;
    private final SellerRepository sellerRepository;
    private final CacheManager cacheManager;
    private final SellerMapper sellerMapper;

    /**
     - Create Shop
     - Input: ShopDTORequest
     - Output: ShopEntity
     - Note: Data will be gotten from Cache and DB (Read Through)
     **/
    @CachePut(value = "shops", key = "#result.seller_id")
    public ShopResponse createShop(ShopDTORequest shopDTO, Long id){
        logger.info("[SHOP_SELLER][createShop] Create shop for seller: {}", id);
        UserEntity user = userService.getUser(id);

        //? Validate

        //! Fail Case

        //* Pass Cade
        logger.info("[SHOP_SELLER][createShop] User found by id: {}", user.getId());
        SellerEntity seller = user.getSeller();
        ShopEntity shop = shopMapper.toEntity(shopDTO);
        shop.setSeller(seller);
        seller.getShops().add(shop);
        sellerRepository.save(seller);
        logger.info("[SHOP_SELLER][createShop] Save Shop {} with Update Seller {} success!", shopDTO.getShopName(), seller.getUserEntity().getId());
        logger.info("[SELLER INFOMATION] {} - {}", seller.getId(), seller.getUserEntity().getId());
        logger.info("[SHOP INFORMATION] {} - {}", shop.getSeller().getId(), shop.getSeller().getUserEntity().getId());
        SellerResponse sellerResponse = sellerMapper.toDTO(seller);
        cacheManager.getCache("sellers").put(seller.getUserEntity().getId(), sellerResponse);

        return shopMapper.toDTO(shop);
    }

    /**
     - Create Shop
     - Type: Internal
     - Input: seller and shop id
     - Output: Shop Entity
     - Note: Data will be gotten from Cache and DB (Read Through)
     **/
    protected ShopEntity getShop(Long seller_id, Long shop_id){
        return shopRepository.findFirstBySeller_IdAndId(seller_id, shop_id)
                .orElseThrow(()-> new RuntimeException("Cannot found shop by seller_id " + seller_id + " and " + " shop_id " + shop_id));
    }
}
