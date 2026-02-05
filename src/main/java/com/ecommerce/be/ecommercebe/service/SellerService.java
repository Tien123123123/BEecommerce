package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerService {
    private final SellerRepository sellerRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SellerMapper sellerMapper;
    private final Logger logger = LoggerFactory.getLogger(SellerService.class);
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     - Promote User to Seller
     - Input: User Id
     - Output: SellerEntity
     - Note: Data will be caught from Cache and DB (Read Through)
     **/
    @CachePut(value = "sellers", key = "#result.id")
    public SellerResponse promoteToSeller(Long id, SellerRegisterDTORequest sellerDTO){
        logger.info("[SELLER_SERVICE][promoteToSeller] promote User to Seller: {}", id);
        UserEntity user = userService.getUser(id);
        UserResponse userDTO = userMapper.toDTO(user);
        // Check User
//        Handler<UserResponse> handler = new ValidateUserStatus();
//        handler.setNext(new ValidateCitizenId(sellerDTO.getCitizenIdentity())); //? Check CCCD
//        ValidateResult<UserResponse> result = handler.validate(userDTO);
//
//
//        // Fail case
//        if(!result.isStatus()){
//            throw new RuntimeException(result.getMessage());
//        }
//
//        // Pass Case
//        logger.info("[SELLER_SERVICE][promoteToSeller] Handler result: {} - {}", result.isStatus(), result.getMessage());
//        logger.info("[SELLER_SERVICE][promoteToSeller] promoting User {} to Seller", user.getFullname());
        SellerEntity seller = sellerMapper.toEntity(sellerDTO);

        seller.setUserEntity(user);
        user.setSeller(seller);

        user.getRoles().add(UserEntity.UserRole.SELLER);
        System.out.println(seller.getUserEntity().getId());
        System.out.println(seller.getCitizenIdentity());

        logger.info("[SELLER_SERVICE][promoteToSeller] Get Seller {} Info before save", seller.getUserEntity().getFullname());
        UserEntity saved = userRepository.save(user);
        // Update User Cache
        UserResponse savedDTO = userMapper.toDTO(saved);
        cacheManager.getCache("users").put(id, savedDTO);


        return sellerMapper.toDTO(saved.getSeller());
    }
    /**
     - Get Seller
     - Input: User id
     - Output: UserEntity
     - Note: Data will be gotten from Cache and DB (Read Through)
     **/
    public SellerEntity getSeller(Long id){
        logger.info("[SELLER_SERVICE][getSeller] Get Seller: {}", id);
        return sellerRepository.findByUserEntity_Id((id))
                .orElseThrow(()->new RuntimeException("Invalid Seller id " + id));
    }

    @Cacheable(value = "sellers", key = "#id")
    public SellerResponse getSellerDetails(Long id){
        logger.info("[SELLER_SERVICE][getSellerDetails] Get Seller: {}", id);
        return sellerMapper.toDTO(getSeller(id));
    }

    /**
     - Delete Seller
     - Input: User id
     - Output: soft-delete = true
     - Note: Data will be gotten from Cache and DB (Read Through)
     **/
    @CacheEvict(value = "sellers", key = "#id")
    public void deleteSeller(Long id){
        SellerEntity seller = getSeller(id);
        SellerResponse sellerDTO = sellerMapper.toDTO(seller);

        logger.info("[SELLER_SERVICE][deleteSeller] Delete Seller: {}", id);
        Handler<SellerResponse> handler = new ValidateSellerStatus();
        ValidateResult<SellerResponse> result = handler.validate(sellerDTO);

        if(result.isStatus()){
            throw new RuntimeException(result.getMessage());
        }
        seller.setSoftDelete(true);
        sellerRepository.save(seller);
    }
}
