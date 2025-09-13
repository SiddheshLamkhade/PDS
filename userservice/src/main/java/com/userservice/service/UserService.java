package com.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userservice.dto.RegisterRequest;
import com.userservice.dto.UserResponse;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
@Service
public class UserService {
    //Service methods for user operations(e.g., register, get user details)
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponse register(RegisterRequest request) {

        repo.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new RuntimeException("Username already taken");
        });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
    user.setRole(Enum.valueOf(com.userservice.model.Role.class, request.getRole()));
        User savedUser= repo.save(user); 
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setRole(savedUser.getRole().name());
        return response;        
    }   

    public UserResponse getUserProfile(Long userId) {
        User user=repo.findById(userId).orElseThrow(()->new RuntimeException("user not  found")); // Placeholder
        UserResponse response = new UserResponse();
        response.setId(user.getId());   
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().name());
        return response;
    }
}
