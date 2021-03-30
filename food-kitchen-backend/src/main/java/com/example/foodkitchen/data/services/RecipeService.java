package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();
}
