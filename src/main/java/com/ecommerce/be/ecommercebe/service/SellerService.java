package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.SellerMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.UserMapper;
import com.ecommerce.be.ecommercebe.model.SellerEntity;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.SellerRepository;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.userhandler.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

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
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     - Promote User to Seller
     - Input: User Id
     - Output: SellerEntity
     - Note: Data will be caught from Cache and DB (Read Through)
     **/
    @CacheEvict(value = "users", key = "#id")
    @CachePut(value = "sellers", key = "#result.id")
    public SellerEntity promoteToSeller(Long id, SellerRegisterDTORequest sellerDTO){
        logger.info("[SELLER_SERVICE][promoteToSeller] promote User to Seller: {}", id);
        UserEntity user = userService.getUser(id);
        UserResponse userDTO = userMapper.toDTO(user);
        // Check User
        UserHandler<UserResponse> handler = new CheckUserStatus(userService);
        handler.setNext(new CheckSellerStatus(userService));
        handler.setNext(new ValidateCitizenId(sellerDTO.getCitizenIdentity()));
        UserCheckResult<?> result = handler.handle(userDTO);
        // Fail case
        if(!result.isStatus()){
            throw new RuntimeException(result.getMessage());
        }

        // Pass Case
        logger.info("[SELLER_SERVICE][promoteToSeller] Handler result: {} - {}", result.isStatus(), result.getMessage());
        logger.info("[SELLER_SERVICE][promoteToSeller] promoting User {} to Seller", user.getFullname());
        SellerEntity seller = sellerMapper.toEntity(sellerDTO);

        seller.setUserEntity(user);
        user.setSeller(seller);

        user.getRoles().add(UserEntity.UserRole.SELLER);
        System.out.println(seller.getUserEntity().getId());
        System.out.println(seller.getCitizenIdentity());

        logger.info("[SELLER_SERVICE][promoteToSeller] Get Seller {} Info before save", seller.getUserEntity().getFullname());
        userRepository.save(user);
        return getSeller(user.getId());
    }
    /**
     - Get Seller
     - Input: User id
     - Output: UserEntity
     - Note: Data will be gotten from Cache and DB (Read Through)
     **/
    @Cacheable(value = "sellers", key = "#id")
    public SellerEntity getSeller(Long id){
        logger.info("[SELLER_SERVICE][getSeller] Get Seller: {}", id);
        return sellerRepository.findByUserEntity_Id((id))
                .orElseThrow(()->new RuntimeException("Invalid Seller id " + id));
    }
}
