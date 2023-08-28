package com.blogsite.auth.service.controller;

import com.blogsite.auth.service.entity.User;
import com.blogsite.auth.service.exception.ServiceException;
import com.blogsite.auth.service.service.JwtTokenUtil;
import com.blogsite.auth.service.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    UserService userService;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @Mock
    Logger log;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() throws Exception {
        when(userService.registerUser(any())).thenReturn(new User("username", "email", "password"));

        String result = userController.register(new User("username", "email", "password"));
        Assertions.assertEquals("username registered successfully", result);
    }

    @Test
    void testAuthenticateUser() throws Exception {
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("generateTokenResponse");

        String result = userController.authenticateUser(new User("username", "email", "password"));
        Assertions.assertEquals("token: generateTokenResponse", result);
    }
}