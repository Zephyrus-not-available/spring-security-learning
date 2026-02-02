package com.kyaw.springsecuritylearning.service;

import com.kyaw.springsecuritylearning.dto.RegisterUserRequest;
import com.kyaw.springsecuritylearning.model.AppUser;
import com.kyaw.springsecuritylearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser createUser(RegisterUserRequest req) {
        AppUser user = new AppUser(req.getUsername(), passwordEncoder.encode(req.getPassword()),
                req.getRoles() == null || req.getRoles().isEmpty() ? "ROLE_USER" : req.getRoles());
        return userRepository.save(user);
    }

    public List<AppUser> listUsers() {
        return userRepository.findAll();
    }
}

