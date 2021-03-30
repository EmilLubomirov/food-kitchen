package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> findAll(){
        return recipeService.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Recipe add(@RequestBody Recipe recipe){
        return recipeService.add(recipe);
    }
}
