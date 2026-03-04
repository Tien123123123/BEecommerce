package com.ecommerce.be.ecommercebe.controller;

import com.ecommerce.be.ecommercebe.dto.request.LoginDTORequest;
import com.ecommerce.be.ecommercebe.dto.request.UserRegisterDTORequest;
import com.ecommerce.be.ecommercebe.dto.response.ResponseData;
import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import com.ecommerce.be.ecommercebe.service.AuthService;
import com.ecommerce.be.ecommercebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseData<String> login(@RequestBody LoginDTORequest dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUserMail(),
                        dto.getPassword()
                )
        );

        String token = authService.generateToken(authentication);

        return new ResponseData<>("Login succeed", HttpStatus.ACCEPTED.value(), token);
    }

    @PostMapping("/register")
    public ResponseData<UserResponse> register(@RequestBody UserRegisterDTORequest dto){
        UserResponse response = userService.createUser(dto);

        return new ResponseData<>("Create User " + response.getUsername() + " succeed!", HttpStatus.CREATED.value(), response);
    }

}
