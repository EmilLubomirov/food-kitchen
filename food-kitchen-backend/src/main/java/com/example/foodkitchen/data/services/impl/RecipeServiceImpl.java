package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.FoodCategory;
import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.recipe.RecipeFilterModel;
import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import com.example.foodkitchen.data.repositories.FoodCategoryRepository;
import com.example.foodkitchen.data.repositories.RecipeRepository;
import com.example.foodkitchen.data.repositories.UserRepository;
import com.example.foodkitchen.data.services.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, FoodCategoryRepository foodCategoryRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RecipeServiceModel> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r, RecipeServiceModel.class))
                .sorted(Comparator.comparing(RecipeServiceModel::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = {"recipes"})
    public List<RecipeServiceModel> findByCategories(RecipeFilterModel recipe) {

        if (recipe.getCategories().size() == 0){
            return findAll();
        }

        List<String> chosenCategories = recipe.getCategories()
                .stream()
                .map(FoodCategoryServiceModel::getName)
                .collect(Collectors.toList());

        return recipeRepository.findAll()
                .stream()
                .filter(r -> r.getCategories()
                        .stream()
                        .map(FoodCategory::getName)
                        .collect(Collectors.toList())
                        .stream()
                        .anyMatch(chosenCategories::contains))
                .collect(Collectors.toList())
                .stream()
                .map(r -> modelMapper.map(r, RecipeServiceModel.class))
                .sorted(Comparator.comparing(RecipeServiceModel::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = {"recipes"}, allEntries = true)
    public RecipeServiceModel add(Recipe recipe, String userUsername) {

        Set<FoodCategory> categories = recipe.getCategories()
                .stream()
                .map(c -> foodCategoryRepository.findByName(c.getName()))
                .collect(Collectors.toSet());

        recipe.setCategories(categories);

        Recipe savedRecipe = recipeRepository.saveAndFlush(recipe);
        User user = userRepository.findByUsername(userUsername);

        savedRecipe.setUser(user);
        Recipe created = recipeRepository.saveAndFlush(savedRecipe);

        if (user.getRecipes() == null){
            user.setRecipes(new HashSet<>(Set.of(created)));
        }

        else {
            user.getRecipes().add(created);
        }

        userRepository.saveAndFlush(user);
        return modelMapper.map(created, RecipeServiceModel.class);
    }

    @Override
    @Cacheable(cacheNames = {"recipe-id"})
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
    @Caching(evict = {
            @CacheEvict(cacheNames = {"recipes"}, allEntries = true),
            @CacheEvict(cacheNames = "recipe-id", key = "#recipe.getId()")
    })
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

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = {"user-favorites"}, allEntries = true),
            @CacheEvict(cacheNames = "recipe-id", key = "#recipe.getId()")
    })
    public RecipeServiceModel addToFavorites(RecipeServiceModel recipe, User user) {

        Recipe recipeById = recipeRepository.findById(recipe.getId()).orElse(null);
        User fan = userRepository.findByUsername(user.getUsername());

        if (recipeById == null || fan == null){
            return recipe;
        }

        if (recipeById.getFans() != null){

            boolean hasAlreadyLiked =
                    recipeById.getFans()
                    .stream()
                    .anyMatch(u -> u.getUsername().equals(user.getUsername()));

            if (!hasAlreadyLiked){
                recipeById.getFans().add(user);
            }

            else {
                return modelMapper.map(recipeById, RecipeServiceModel.class);
            }
        }

        else {
            recipeById.setFans(Set.of(user));
        }

        return modelMapper.map(recipeRepository.saveAndFlush(recipeById),
                RecipeServiceModel.class);
    }

    @Override
    @Cacheable(cacheNames = {"user-favorites"})
    public List<RecipeServiceModel> findFavoriteRecipes(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null){
            return null;
        }

        return user.getFavorites()
                .stream()
                .map(r -> modelMapper.map(r, RecipeServiceModel.class))
                .sorted(Comparator.comparing(RecipeServiceModel::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = {"user-favorites"}, allEntries = true),
            @CacheEvict(cacheNames = "recipe-id", key = "#recipeId")
    })
    public RecipeServiceModel deleteFromFavorites(String recipeId, String username) {

        User user = userRepository.findByUsername(username);
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        if (user == null || recipe == null){
            return null;
        }

        Set<Recipe> updatedRecipes = user.getFavorites()
                .stream()
                .filter(r -> !r.getId().equals(recipeId))
                .collect(Collectors.toSet());

        user.setFavorites(updatedRecipes);
        userRepository.saveAndFlush(user);

        Set<User> updatedFans = recipe.getFans()
                .stream()
                .filter(f -> !f.getUsername().equals(username))
                .collect(Collectors.toSet());

        recipe.setFans(updatedFans);

        return modelMapper.map(recipeRepository.saveAndFlush(recipe),
                RecipeServiceModel.class);
    }
}
