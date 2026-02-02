package com.kyaw.springsecuritylearning.controller;

import com.kyaw.springsecuritylearning.dto.RegisterUserRequest;
import com.kyaw.springsecuritylearning.dto.UserResponse;
import com.kyaw.springsecuritylearning.model.AppUser;
import com.kyaw.springsecuritylearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponse register(@RequestBody RegisterUserRequest req) {
        AppUser created = userService.createUser(req);
        return new UserResponse(created.getId(), created.getUsername(), created.getRoles());
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.listUsers().stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getRoles()))
                .collect(Collectors.toList());
    }

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        if (authentication == null) return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            AppUser u = userService.listUsers().stream().filter(x -> x.getUsername().equals(username)).findFirst().orElse(null);
            if (u == null) return null;
            return new UserResponse(u.getId(), u.getUsername(), u.getRoles());
        }
        return null;
    }
}

