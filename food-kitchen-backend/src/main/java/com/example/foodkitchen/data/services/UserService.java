package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel addRecipe(String id, Recipe recipe);

    boolean existsByUsername(String username);

    UserServiceModel findByUsername(String username);
}
