package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.SellerMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.UserMapper;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.SellerRepository;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import com.ecommerce.be.ecommercebe.service.handler.userhandler.*;
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
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SellerService.class);
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final HandlerFactory handlerFactory;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SellerMapper sellerMapper;
    private final UserMapper userMapper;
    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager entityManager;

    /**
     * - Description: Promote user to seller
     * - Type: Public
     * - Input: DTO data and User id
     * - Output: DTO data
     * - Note: Data will be saved in DB and Cache (Write Back)
     * ! Cache save DTO data
     **/
    @CachePut(value = "sellers", key = "#result.id")
    public SellerResponse promoteToSeller(Long id, SellerRegisterDTORequest sellerDTO) {
        logger.info("[SELLER_SERVICE][promoteToSeller] promote User to Seller: {}", id);
        UserEntity user = userService.getUser(id); // Get undeleted user
        sellerDTO.setId(user.getId());

        // Check User
        Handler<SellerRegisterDTORequest> sellerHandler = handlerFactory.getChain(SellerRegisterDTORequest.class);
        if (sellerHandler != null) {
            ValidateResult<? extends BaseValidate> result = sellerHandler.validate(sellerDTO);
            if (!result.isStatus()) {
                logger.warn("[USER_SERVICE][createUser] error: {}", result.getMessage());
                throw new RuntimeException(result.getMessage());
            }
        } else {
            logger.warn("No validations chain for UserRegisterDTORequest");
        }

        SellerEntity seller = sellerRepository.findByUserIdIncludingDeleted(id).orElse(null);

        if (seller == null) {
            seller = sellerMapper.toEntity(sellerDTO);
            seller.setUserEntity(user);
        } else {
            seller.setCitizenIdentity(sellerDTO.getCitizenIdentity());
            Boolean isDeleted = sellerRepository.isSoftDeleted(seller.getId());
            if (Boolean.TRUE.equals(isDeleted)) {
                sellerRepository.restoreById(seller.getId());
                entityManager.flush();
                entityManager.refresh(seller);
            }
        }

        // ? Set Seller
        user.setSeller(seller);
        // ? Add Role
        user.getRoles().add(UserEntity.UserRole.SELLER);

        logger.info("[SELLER_SERVICE][promoteToSeller] Get Seller {} Info before save",
                seller.getUserEntity().getFullname());

        seller = sellerRepository.save(seller);
        entityManager.flush();

        UserEntity saved = userRepository.save(user);
        // ? Update User Cache
        UserResponse savedDTO = userMapper.toDTO(saved);
        cacheManager.getCache("users").put(id, savedDTO);

        return sellerMapper.toDTO(saved.getSeller());
    }

    /**
     * - Description: Get seller by id
     * - Type: Internal
     * - Input: Id (User id)
     * - Output: Seller Entity
     **/
    protected SellerEntity getSeller(Long id) {
        logger.info("[SELLER_SERVICE][getSeller] Get Seller: {}", id);
        return sellerRepository.findByUserEntity_Id((id))
                .orElseThrow(() -> new RuntimeException("Invalid Seller id " + id));
    }

    /**
     * - Description: Get seller by id
     * - Type: Public
     * - Input: Id (User id)
     * - Output: DTO data
     * - Note: Data will be gotten from Cache then DB (Read Through)
     * ! Cache save DTO data
     **/
    @Cacheable(value = "sellers", key = "#id")
    public SellerResponse getSellerDetails(Long id) {
        logger.info("[SELLER_SERVICE][getSellerDetails] Get Seller: {}", id);
        return sellerMapper.toDTO(getSeller(id));
    }

    /**
     * - Description: Delete seller by Id
     * - Type: Public
     * - Input: Id (User id)
     * - Output: Soft-delete = true
     **/
    @CacheEvict(value = "sellers", key = "#id")
    public void deleteSeller(Long id) {
        logger.info("[SELLER_SERVICE][deleteSeller] Delete Seller: {}", id);
        SellerEntity seller = getSeller(id); // User id
        sellerRepository.deleteById(seller.getId());
    }

    /**
     * - Description: Restore seller by Id
     * - Type: Public
     * - Input: Id (User id)
     * - Output: Soft-delete = true
     **/
    @CacheEvict(value = "sellers", key = "#id")
    public SellerResponse restoreSeller(Long id) {
        logger.info("[SELLER_SERVICE][restoreSeller] Restore Seller: {}", id);
        SellerEntity seller = sellerRepository.findByUserIdIncludingDeleted(id)
                .orElseThrow(() -> new RuntimeException("Cannot found user by id " + id));
        sellerRepository.restoreById(id);
        logger.info("[SELLER_SERVICE][restoreSeller] Restore seller {} successfully", id);
        return getSellerDetails(id);
    }

}
