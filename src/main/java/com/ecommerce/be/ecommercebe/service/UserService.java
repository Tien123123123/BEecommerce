package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.UserMapper;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import com.ecommerce.be.ecommercebe.service.handler.userhandler.*; // Keep wildcard as in original if needed? Or specific imports? Clean imports.
import com.ecommerce.be.ecommercebe.util.HandlerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final HandlerFactory handlerFactory;
    private final UserMapper userMapper;

    /**
     ** - Description: Create User from User DTO
     ** - Type: Public
     ** - Input: DTO data
     ** - Output: DTO data
     ** - Note: Data will be saved in DB and Cache (Write Back)
     ** ! Cache save DTO data
     **/
    @CachePut(value = "users", key = "#result.id")
    public UserResponse createUser(UserRegisterDTORequest userDTO) {
        logger.info("[USER_SERVICE][createUser] create user: {}", userDTO.getFullname());
        /**
         * - Set chain to validate Input data
         * - Use .setNext to apply next validated function
         * - Functions in handler/userhandler
         **/
        Handler<UserRegisterDTORequest> userHandler = handlerFactory.getChain(UserRegisterDTORequest.class);
        if (userHandler != null) {
            ValidateResult<? extends BaseValidate> result = userHandler.validate(userDTO);
            if (!result.isStatus()) {
                logger.warn("[USER_SERVICE][createUser] error: {}", result.getMessage());
                throw new RuntimeException(result.getMessage());
            }
        } else {
            logger.warn("No validations chain for UserRegisterDTORequest");
        }
        UserEntity user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity saved = userRepository.save(user);

        logger.info("[USER_SERVICE][createUser] User Entity Key: {} - Name: {}", saved.getId(), saved.getUsername());
        UserResponse userResponse = userMapper.toDTO(saved);
        userResponse.setSoftDelete(userRepository.isSoftDeleted(userResponse.getId()));
        return userResponse;
    }

    /**
     ** - Description: Get user by id
     ** - Type: Internal
     ** - Input: Id
     ** - Output: User entity
     ** - Note: Data will be gotten from DB
     **/
    protected UserEntity getUser(Long id) {
        logger.info("[USER_SERVICE][getUser] get User : {}", id);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid User By Id: " + id));
        return user;
    }

    /**
     ** - Description: Get user by id
     ** - Type: Public
     ** - Input: Id
     ** - Output: DTO data
     ** - Note: Data will be gotten from Cache then DB (Read Through)
     ** ! Cache save DTO data
     **/
    @Cacheable(value = "users", key = "#id")
    public UserResponse getUserDetail(Long id) {
        UserEntity user = getUser(id);
        UserResponse userResponse = userMapper.toDTO(user);
        userResponse.setSoftDelete(userRepository.isSoftDeleted(id));
        return userResponse;
    }

    /**
     ** - Description: Delete User base on Id
     ** - Type: Public
     ** - Input: Id
     ** - Output: Soft-delete = true
     ** - Note: Cache will remove data after succeed!
     **/
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        logger.info("[USER_SERVICE][deleteUser] Deleting user: {}", id);
        if (userRepository.isSoftDeleted(id)) {
            throw new RuntimeException("This user is already deleted!");
        }
        userRepository.softDeleteById(id);
    }

    /**
     ** - Description: Restore soft-deleted User by Id
     ** - Type: Public
     ** - Input: User Id
     ** - Output: Soft-delete = false
     ** - Note: Cache to update
     **/
    @CachePut(value = "users", key = "#id")
    public UserResponse restoreUser(Long id) {
        logger.info("[USER_SERVICE][restoreUser] recovering deleted user: {}", id);
        if (!userRepository.isSoftDeleted(id)) {
            logger.warn("User {} is not deleted!", id);
        }
        userRepository.restoreById(id);
        UserEntity user = getUser(id);
        logger.info("[USER_SERVICE][restoreUser] user roles: {} ", user.getRoles());

        logger.info("[USER_SERVICE][restoreUser] user {} is restored successfully!", user.getId());
        UserResponse userResponse = userMapper.toDTO(user);
        userResponse.setSoftDelete(userRepository.isSoftDeleted(id));
        return userResponse;
    }
}
