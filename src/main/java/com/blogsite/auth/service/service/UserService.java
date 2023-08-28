package com.blogsite.auth.service.service;

import com.blogsite.auth.service.entity.User;
import com.blogsite.auth.service.exception.ServiceException;
import com.blogsite.auth.service.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) throws ServiceException {
        log.info("inside user input validation method");
        String regex = "^[a-zA-Z0-9]+$";
        if(user.getPassword().length()<8){
            throw new ServiceException("Password should have more than 8 characters");
        }
        if(!(user.getPassword().matches(regex))){
            throw new ServiceException("Password should be Alphanumeric");
        }
        final String emailRegex = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";;
        if(!(user.getEmail().matches(emailRegex))){
            throw new ServiceException("Email format is incorrect");
        }
        User username = userRepository.findByUsername(user.getUsername());
        if(username!=null){
            throw new ServiceException("Username already exists");
        }
        User email = userRepository.findByEmail(user.getEmail());
        if(email!=null){
            throw new ServiceException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new ServiceException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }
}
