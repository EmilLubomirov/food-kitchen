package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe add(Recipe recipe);

    Recipe findById(String id);
}
