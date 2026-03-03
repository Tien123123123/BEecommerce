package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.constant.user.UserStatus;
import com.ecommerce.be.ecommercebe.dto.request.ShipperDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ShipperResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.ShipperMapper;
import com.ecommerce.be.ecommercebe.model.ShipperEntity;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.ShipperRepository;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipperService {
    private static final Logger logger = LoggerFactory.getLogger(ShipperService.class);
    private final UserRepository userRepository;

    private final ShipperRepository shipperRepository;

    private final ShipperMapper shipperMapper;

    private final UserService userService;

    @Caching(
            evict = {
                    @CacheEvict(value = "users", key = "#dto.userId")
            },
            put = {
                    @CachePut(value = "shippers", key = "#dto.userId")
            }
    )
    public ShipperResponse createShipper(ShipperDTORequest dto){
        UserEntity user = userService.getUser(dto.getUserId());

        // Validate

        //
        ShipperEntity shipperEntity = shipperMapper.toEntity(dto);
        shipperEntity.setStatus(UserStatus.ACTIVE);
        user.promoteToShipper(shipperEntity);

        ShipperEntity saved = shipperRepository.save(shipperEntity);

        return shipperMapper.toDTO(saved);
    }
}
