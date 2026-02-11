package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
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
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import com.ecommerce.be.ecommercebe.util.HandlerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);
    private final ShopRepository shopRepository;
    private final UserService userService;
    private final SellerRepository sellerRepository;
    private final CacheManager cacheManager;
    private final SellerService sellerService;
    private final HandlerFactory handlerFactory;
    private final UserRepository userRepository;
    private final ShopMapper shopMapper;
    private final SellerMapper sellerMapper;
    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager entityManager;

    /**
     * - Description: Create Shop
     * - Type: Public
     * - Input: ShopDTO and User id
     * - Output: DTO data
     * - Note: Data will be saved in DB and Cache (Write Back)
     * ! Cache save DTO data
     **/
    @CachePut(value = "shops", key = "#result.seller_id")
    public ShopResponse createShop(ShopDTORequest shopDTO, Long id) {
        logger.info("[SHOP_SELLER][createShop] Create shop for seller: {}", id);
        shopDTO.setSellerId(id);

        // ? Validate
        Handler<ShopDTORequest> sellerHandler = handlerFactory.getChain(ShopDTORequest.class);
        if (sellerHandler != null) {
            ValidateResult<? extends BaseValidate> result = sellerHandler.validate(shopDTO);
            if (!result.isStatus()) {
                logger.warn("[SHOP_SERVICE][createShop] error: {}", result.getMessage());
                throw new RuntimeException(result.getMessage());
            }
        } else {
            logger.warn("No validations chain for ShopDTORequest");
        }
        // * Pass Cade
        logger.info("[SHOP_SELLER][createShop] Get seller by id: {}", id);
        SellerEntity seller = sellerService.getSeller(id);

        ShopEntity shop = shopRepository.findShopEntityById_IncludingDeleted(id).orElse(null);

        if (shop == null) {
            shop = shopMapper.toEntity(shopDTO);
            shop.setSeller(seller);
        } else {
            shop.setShopName(shopDTO.getShopName());
            shop.setShopAddress(shopDTO.getShopAddress());

            Boolean isDeleted = shopRepository.isSoftDeleted(id);
            if (Boolean.TRUE.equals(isDeleted)) {
                shopRepository.restoreById(id);
                entityManager.refresh(shop);
            }
        }

        // ? Put data into DB
        shop = shopRepository.save(shop);
        entityManager.flush();

        seller.setShop(shop);
        // ? Put data into Cache (Redis)
        SellerResponse sellerResponse = sellerMapper.toDTO(seller);
        cacheManager.getCache("sellers").put(seller.getUserEntity().getId(), sellerResponse);

        return shopMapper.toDTO(shop);
    }

    /**
     * - Description: Get Shop
     * - Type: Internal
     * - Input: Shop_id or Seller_id (User_id)
     * - Output: Entity data
     * - Note: Data will be gotten from Cache to DB (Read Through)
     * ! Cache save DTO data
     * - Modified: findFirstById might check deleted if annotation not working, but
     * usually checks active.
     **/
    protected ShopEntity getShop(Long shop_id) {
        return shopRepository.findById(shop_id)
                .orElseThrow(() -> new RuntimeException("Shop not found for seller/user id: " + shop_id));
    }

    /**
     * - Description: Get Shop details
     * - Type: Public
     * - Input: Shop_id or Seller_id (User_id)
     * - Output: DTO data
     * - Note: Data will be gotten from Cache to DB (Read Through)
     * ! Cache save DTO data
     **/
    @Cacheable(value = "shops", key = "#user_id")
    public ShopResponse getShopDetail(Long user_id) {
        return shopMapper.toDTO(getShop(user_id));
    }

    /**
     * - Description: Soft-delete Shop
     * - Type: Public
     * - Input: Shop_id or Seller_id (User_id)
     * - Output: deleted shop
     **/
    @CacheEvict(value = "shops", key = "#shop_id")
    public void deleteShop(Long shop_id) {
        logger.info("[SHOP_SELLER][deleteShop] Delete shop by id: {}", shop_id);
        ShopEntity shop = getShop(shop_id);
        shopRepository.deleteById(shop.getId());
    }

    /**
     * - Description: restore Shop
     * - Type: Public
     * - Input: Shop_id or Seller_id (User_id)
     * - Output: restored shop
     **/
    @CachePut(value = "shops", key = "#shop_id")
    public ShopResponse restoreShop(Long shop_id) {
        logger.info("[SHOP_SELLER][restoreShop] Restore shop by id: {}", shop_id);
        ShopEntity shop = shopRepository.findShopEntityById_IncludingDeleted(shop_id)
                .orElseThrow(() -> new RuntimeException("Shop not found (including deleted) for id: " + shop_id));

        shopRepository.restoreById(shop.getId());
        entityManager.refresh(shop);

        return shopMapper.toDTO(shop);
    }
}
