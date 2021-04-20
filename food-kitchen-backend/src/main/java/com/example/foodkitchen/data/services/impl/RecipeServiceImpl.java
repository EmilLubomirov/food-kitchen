package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import com.example.foodkitchen.data.repositories.RecipeRepository;
import com.example.foodkitchen.data.repositories.UserRepository;
import com.example.foodkitchen.data.services.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RecipeServiceModel> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Recipe add(Recipe recipe, String userUsername) {

        Recipe savedRecipe = recipeRepository.saveAndFlush(recipe);
        User user = userRepository.findByUsername(userUsername);

        savedRecipe.setUser(user);
        recipeRepository.saveAndFlush(savedRecipe);

        if (user.getRecipes() == null){
            user.setRecipes(new HashSet<>(Set.of(savedRecipe)));
        }

        else {
            user.getRecipes().add(recipe);
        }

        userRepository.saveAndFlush(user);
        return recipe;
    }

    @Override
    public RecipeServiceModel findById(String id) {

        Recipe recipe = recipeRepository.findById(id).orElse(null);

        if (recipe != null){
            RecipeServiceModel model = modelMapper.map(recipe, RecipeServiceModel.class);
            model.setPublisher(recipe.getUser().getUsername());
            return model;
        }

        return null;
    }

    @Override
    public RecipeServiceModel updateRating(RecipeServiceModel recipe, User user) {

        Recipe recipeById = recipeRepository.findById(recipe.getId()).orElse(null);
        User voter = userRepository.findByUsername(user.getUsername());

        if (recipeById == null || voter == null){
            return null;
        }

        boolean hasVoted = voter.getRatedRecipes()
                .stream()
                .anyMatch(r -> r.getId().equals(recipe.getId()));

        if (hasVoted){
            return recipe;
        }

        double avgRating = recipe.getRating();

        if (recipeById.getRating() > 0){
            avgRating = (recipeById.getRating() + recipe.getRating()) / 2.0;
        }

        recipeById.setRating(avgRating);

        if (recipeById.getVoters() == null){
            recipeById.setVoters(Set.of(user));
        }

        else {
            recipeById.getVoters().add(voter);
        }

        return modelMapper.map(recipeRepository.saveAndFlush(recipeById),
                RecipeServiceModel.class);
    }
}
