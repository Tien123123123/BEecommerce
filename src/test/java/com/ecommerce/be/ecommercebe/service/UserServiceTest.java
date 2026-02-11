package com.ecommerce.be.ecommercebe.service;

import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.model.UserEntity;
import com.ecommerce.be.ecommercebe.repository.UserRepository;
import com.ecommerce.be.ecommercebe.service.handler.Handler;
import com.ecommerce.be.ecommercebe.service.handler.ValidateResult;
import com.ecommerce.be.ecommercebe.util.HandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HandlerFactory handlerFactory;

    @InjectMocks
    private UserService userService;

    @Mock
    private Handler<UserRegisterDTORequest> handler;

    private UserRegisterDTORequest userRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRequest = new UserRegisterDTORequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password123");
        userRequest.setFullname("Test User");
        userRequest.setEmail("test@example.com");
    }

    @Test
    void createUser_Success() {
        // Arrange
        when(handlerFactory.getChain(UserRegisterDTORequest.class)).thenReturn(handler);
        ValidateResult result = new ValidateResult(true, "Success", null);
        when(handler.validate(any())).thenReturn(result);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        // Act
        UserResponse response = userService.createUser(userRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createUser_ValidationFail() {
        // Arrange
        when(handlerFactory.getChain(UserRegisterDTORequest.class)).thenReturn(handler);
        ValidateResult result = new ValidateResult(false, "Validation failed", null);
        when(handler.validate(any())).thenReturn(result);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userRequest);
        });
        assertEquals("Validation failed", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
