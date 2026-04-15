package com.example.auth_service.service;

import com.example.auth_service.entity.User;
import com.example.auth_service.repo.UserRepository;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
