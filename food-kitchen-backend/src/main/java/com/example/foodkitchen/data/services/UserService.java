package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.UserServiceModel;

public interface UserService {

    UserServiceModel register(UserServiceModel userServiceModel);

    boolean existsByUsername(String username);
}
