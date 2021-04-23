package com.example.foodkitchen.data.models.service;

import com.example.foodkitchen.data.entities.base.BaseInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeServiceModel extends BaseInfoEntity {

    private String imageUrl;
    private String publisher;
    private double rating;
    private Set<UserServiceModel> voters;
    private Set<UserServiceModel> fans;
}
