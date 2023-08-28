package com.blogsite.auth.service.service;

import com.blogsite.auth.service.entity.User;
import com.blogsite.auth.service.exception.ServiceException;
import com.blogsite.auth.service.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    Logger log;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() throws ServiceException {
//        when(userRepository.findByUsername(anyString())).thenReturn(new User("uniqueuser", "email1@gmail.com", "password123"));
//        when(userRepository.findByEmail(anyString())).thenReturn(new User("uniqueuser2", "email2@gmail.com", "password123"));

        User result = userService.registerUser(new User("demouser", "email@gmail.com", "password123"));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(new User("username", "email", "password"));

        UserDetails result = userService.loadUserByUsername("username");
        Assertions.assertNotNull(result);
    }
}