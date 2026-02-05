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
    private final UserMapper userMapper;
    private final SellerMapper sellerMapper;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC = "user-events";

    /**
     - Method Post
     **/
    @PostMapping
    public ResponseData<UserResponse> createUser(@RequestBody UserRegisterDTORequest userDTO){
        logger.info("[USER_CONTROLLER] Create User: {}", userDTO.getFullname());
        UserResponse user = userService.createUser(userDTO);
        return new ResponseData<UserResponse>("User created!", HttpStatus.CREATED.value(), user);
    }
    /**
     - Method Patch
     - Note: Update user to buyer
     **/
    @PatchMapping("/{id}")
    public ResponseData<SellerResponse> updateSeller(@PathVariable Long id, @RequestBody SellerRegisterDTORequest sellerDTO){
        logger.info("[USER_CONTROLLER] Update user to seller: {}", id);
        SellerResponse seller = sellerService.promoteToSeller(id, sellerDTO);
        return new ResponseData<SellerResponse>("User promoted to Seller!", HttpStatus.CREATED.value(), seller);
    }
    /**
     - Method Get
     **/
    @GetMapping("/{id}")
    public ResponseData<UserResponse> getUser(@PathVariable Long id){
        logger.info("[USER_CONTROLLER] Get User: {}", id);
        UserResponse user = userService.getUserDetail(id);
//        kafkaTemplate.send(TOPIC, String.valueOf(user.getId()), user);
//        logger.info("[USER_CONTROLLER] Send userDTO to Kafka with Topic: {} - user Id: {}", TOPIC, user.getId());
        return new ResponseData<UserResponse>("User info!", HttpStatus.FOUND.value(), user);
    }
    @GetMapping("/seller/{id}")
    public ResponseData<SellerResponse> getSeller(@PathVariable Long id){
        logger.info("[USER_CONTROLLER] Get Seller: {}", id);
        SellerResponse seller = sellerService.getSellerDetails(id);
        return new ResponseData<SellerResponse>("Seller info!", HttpStatus.FOUND.value(), seller);
    }
    /**
     - Method Delete
     **/
    @DeleteMapping("/{id}")
    public ResponseData<UserResponse> deleteUser(@PathVariable Long id){
        logger.info("[USER_CONTROLLER] Delete User: {}", id);
        userService.deleteUser(id);

        return new ResponseData<UserResponse>("User " + id + " deleted!", HttpStatus.ACCEPTED.value(), null);
    }
}
