package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.models.binding.user.UserRegisterModel;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import com.example.foodkitchen.data.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(UserRegisterModel userRegisterModel){

        if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())){
            return ResponseEntity.badRequest()
                    .body("Passwords do not match!");
        }

        if (userService.existsByUsername(userRegisterModel.getUsername())){
            return ResponseEntity.badRequest()
                    .body("User with such username already exists!");
        }

        userService.register(modelMapper.map(userRegisterModel, UserServiceModel.class));
        return ResponseEntity.ok().body("User is registered successfully");
    }
}
