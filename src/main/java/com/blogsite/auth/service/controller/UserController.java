package com.blogsite.auth.service.controller;

import com.blogsite.auth.service.entity.User;
import com.blogsite.auth.service.exception.ServiceException;
import com.blogsite.auth.service.service.JwtTokenUtil;
import com.blogsite.auth.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/blogsite")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    KafkaProducerConfig config = new KafkaProducerConfig();

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(value="/user/register")
    public String register(@RequestBody User user) throws Exception {
        log.info("inside register user controller");
        userService.registerUser(user);
//        config.sendLogToKafka(user.getUsername() + " registered successfully");
        return user.getUsername() + " registered successfully";
    }

    @PostMapping(value="/user/login")
    public String authenticateUser(@RequestBody User authenticationUser) throws Exception {
        try {
            log.info("inside login controller");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationUser.getUsername(), authenticationUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (Exception e){
            throw new ServiceException("Invalid username or password");
        }
//        config.sendLogToKafka("Authentication successful for user: "+authenticationUser.getUsername());
//        config.sendLogToKafka("Generated JWT token: "+jwtTokenUtil.generateToken(authenticationUser.getUsername()));
        return "token: "+jwtTokenUtil.generateToken(authenticationUser.getUsername());
    }

}
