package com.example.foodkitchen.data.models.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceModel {

    private String username;
    private String password;
    private String avatarImageUrl;

    @JsonIgnore
    private Set<RoleServiceModel> authorities;

    @JsonIgnore
    private Set<RecipeServiceModel> recipes;
}
