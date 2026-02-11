package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.service.SellerService;
import com.ecommerce.be.ecommercebe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import com.ecommerce.be.ecommercebe.config.SecureConfig;
import com.ecommerce.be.ecommercebe.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({ SecureConfig.class, GlobalExceptionHandler.class })
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private SellerService sellerService;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRegisterDTORequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRegisterDTORequest();
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setFullname("Test User");
        userRequest.setPassword("password123");
        userRequest.setConfirm_password("password123");
        userRequest.setPhone("0123456789");

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setUsername("testuser");
        userResponse.setEmail("test@example.com");
        userResponse.setFullname("Test User");
        userResponse.setRoles(Collections.singleton("ROLE_USER"));
    }

    @Test
    @WithMockUser
    void createUser_Success() throws Exception {
        when(userService.createUser(any(UserRegisterDTORequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created!"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.object.id").value(1L))
                .andExpect(jsonPath("$.object.username").value("testuser"));

        verify(kafkaTemplate).send(eq("user-events"), eq("1"), any(UserResponse.class));
    }

    @Test
    @WithMockUser
    void createUser_ValidationError() throws Exception {
        when(userService.createUser(any(UserRegisterDTORequest.class)))
                .thenThrow(new RuntimeException("Validation failed: Email already exists"));

        mockMvc.perform(post("/api/user")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Validation failed: Email already exists"));
    }
}
