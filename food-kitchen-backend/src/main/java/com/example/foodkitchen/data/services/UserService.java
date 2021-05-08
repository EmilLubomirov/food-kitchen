package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel addRecipe(String id, Recipe recipe);

    boolean existsByUsername(String username);

    UserServiceModel findByUsername(String username);

    UserServiceModel editUser(String oldUsername, String updateUsername,
                              String oldPass,String updatePass, String updatePass2);

    UserServiceModel editUserUsername(String oldUsername, String updateUsername);

    UserServiceModel editUserPassword(String username, String oldPassword, String updatePassword);

    UserServiceModel editUserProfilePicture(String username, String updateAvatarImageUrl);
}
