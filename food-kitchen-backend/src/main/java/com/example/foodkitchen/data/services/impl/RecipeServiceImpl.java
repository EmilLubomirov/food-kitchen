package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.repositories.RecipeRepository;
import com.example.foodkitchen.data.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe add(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public Recipe findById(String id) {
        return recipeRepository.findById(id).orElse(null);
    }
}
