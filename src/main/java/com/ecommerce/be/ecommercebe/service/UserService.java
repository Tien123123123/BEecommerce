package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.UserMapper;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.userhandler.*;
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
    private final UserMapper userMapper;

    /**
     - Create User from User DTO
     - Input: UserRegisterDTORequest
     - Output: UserEntity
     - Note: Data will be saved in DB and Cache (Write Back)
     **/
    @CachePut(value = "users", key = "#result.id")
    public UserEntity createUser(UserRegisterDTORequest userDTO){
        logger.info("[USER_SERVICE][createUser] create user: {}", userDTO.getFullname());
        /**
         - Set chain to validate Input data
         - Use .setNext to apply next validated function
         - Functions in handler/userhandler
         **/
        UserHandler<UserRegisterDTORequest> handler = new ValidateValidPassword();
        handler.setNext(new ValidateUserExists(userRepository));

        UserCheckResult<?> result = handler.handle(userDTO);
        if(!result.isStatus()){
            throw new RuntimeException(result.getMessage());
        }
        logger.info("[USER_SERVICE][createUser] Handler result: {} - {}", result.isStatus(), result.getMessage());

        UserEntity user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setActive(true); // activate user
        UserEntity saved = userRepository.save(user);
        logger.info("[USER_SERVICE][createUser] User Entity Key: {} - Name: {}", saved.getId(), saved.getUsername());

        return saved;
    }
    /**
     - Get User by Id
     - Input: User Id
     - Output: UserEntity
     - Note: Data will be gotten from Cache and DB (Read Through)
     **/
    @Cacheable(value = "users", key = "#id")
    public UserEntity getUser(Long id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Invalid User By Id: " + id));
        return user;
    }
    /**
     - Soft delete User by Id
     - Input: User Id
     - Output: user soft-delete = true
     - Note: Data will only be removed from Cache
     **/
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id){
        logger.info("[USER_SERVICE][deleteUser] Deleting user: {}", id );
        UserEntity user = getUser(id);
        if(user.isSoft_delete()){
            throw new RuntimeException("This user is banned!");
        }
        user.setSoft_delete(true);
        logger.info("User {} is banned!", user.getUsername());
    }
    /**
     - Restore soft-deleted User by Id
     - Input: User Id
     - Output: user soft-delete = false
     **/
    @CacheEvict(value = "users", key = "#id")
    @CachePut(value = "users", key = "#id")
    public UserEntity restoreUser(Long id){
        logger.info("[USER_SERVICE][restoreUser] recovering deleted user: {}", id );
        UserEntity user = getUser(id);

        if(!user.isSoft_delete()){
            throw new RuntimeException("User is not deleted!");
        }

        user.setSoft_delete(false);
        logger.info("User {} is restored", user.getUsername());

        return userRepository.save(user);
    }
}
