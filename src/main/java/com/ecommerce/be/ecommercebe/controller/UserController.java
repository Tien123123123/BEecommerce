package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.SellerRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.SellerResponse;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.dto.response.mapper.SellerMapper;
import com.ecommerce.be.ecommercebe.dto.response.mapper.UserMapper;
import com.ecommerce.be.ecommercebe.service.SellerService;
import com.ecommerce.be.ecommercebe.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final SellerService sellerService;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC = "user-events";

    /**
     * - Description: Create User
     * - Method Post
     * - API: /api/user
     **/
    @PostMapping
    public ResponseData<UserResponse> createUser(@Valid @RequestBody UserRegisterDTORequest userDTO) {
        logger.info("[USER_CONTROLLER] Create User: {}", userDTO.getFullname());
        UserResponse user = userService.createUser(userDTO);

        return new ResponseData<UserResponse>("User created!", HttpStatus.CREATED.value(), user);
    }

    /**
     * - Description: Get User details
     * - Method Get
     * - API: /api/user/id
     **/
    @GetMapping("/{id}")
    public ResponseData<UserResponse> getUser(@PathVariable Long id) {
        logger.info("[USER_CONTROLLER] Get User: {}", id);
        UserResponse user = userService.getUserDetail(id);
        // kafkaTemplate.send(TOPIC, String.valueOf(user.getId()), user);
        // logger.info("[USER_CONTROLLER] Send userDTO to Kafka with Topic: {} - user
        // Id: {}", TOPIC, user.getId());
        return new ResponseData<UserResponse>("User info!", HttpStatus.FOUND.value(), user);
    }

    /**
     * - Description: Delete User
     * - Method Delete
     * - API: /api/user/id
     **/
    @DeleteMapping("/{id}")
    public ResponseData<UserResponse> deleteUser(@PathVariable Long id) {
        logger.info("[USER_CONTROLLER] Delete User: {}", id);
        userService.deleteUser(id);

        return new ResponseData<UserResponse>("User " + id + " deleted!", HttpStatus.ACCEPTED.value(), null);
    }
    /**
     * - Description: Restore User
     * - Method Patch
     * - API: /api/user/id
     **/
    @PatchMapping("/restore/{id}")
    public ResponseData<UserResponse> restoreUser(@PathVariable Long id){
        logger.info("[USER_CONTROLLER] Restore User: {}", id);
        UserResponse user = userService.restoreUser(id);

        return new ResponseData<UserResponse>("User " + id + " restored!", HttpStatus.ACCEPTED.value(), user);
    }
}
