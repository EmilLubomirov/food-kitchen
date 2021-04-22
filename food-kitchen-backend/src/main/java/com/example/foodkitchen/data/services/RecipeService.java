package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.recipe.RecipeFilterModel;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;

import java.util.List;

public interface RecipeService {

    List<RecipeServiceModel> findAll();

    List<RecipeServiceModel> findByCategories(RecipeFilterModel recipe);

    Recipe add(Recipe recipe, String userId);

    RecipeServiceModel findById(String id);

    RecipeServiceModel updateRating(RecipeServiceModel recipe, User user);
}
