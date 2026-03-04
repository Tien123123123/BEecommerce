package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.constant.user.UserStatus;
import com.ecommerce.be.ecommercebe.dto.request.ShipperDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ShipperResponse;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipperService {
    private static final Logger logger = LoggerFactory.getLogger(ShipperService.class);

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

    public ShipperEntity getShipper(Long userId){
        return shipperRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Cannot find shipper by id " + userId));
    }
    @Cacheable(value = "shippers", key = "#userId", unless = "#result == null")
    public ShipperResponse getShipperDetail(Long userId){
        return shipperMapper.toDTO(getShipper(userId));
    }

    @CacheEvict(value = "shippers", key = "#userId")
    public void deleteShipper(Long userId){
        UserEntity user = userService.getUser(userId);
        ShipperEntity shipperEntity = getShipper(user.getId());

        // Validate

        //
        shipperRepository.delete(shipperEntity);
    }

    @CacheEvict(value = "shippers", key = "#userId")
    public ShipperResponse restoreShipper(Long userId){
        UserEntity user = userService.getUser(userId);

        // Validate
        Boolean isDeleted = shipperRepository.isSoftDeleted(user.getId());
        if(Boolean.TRUE.equals(isDeleted)){
            shipperRepository.restoreById(userId);
        }else{
            throw new RuntimeException("Shipper " + user.getId() + " is not banned!");
        }
        //
        ShipperEntity restored = shipperRepository.getShipperIncludeDeleted(userId);

        return shipperMapper.toDTO(restored);
    }

}
