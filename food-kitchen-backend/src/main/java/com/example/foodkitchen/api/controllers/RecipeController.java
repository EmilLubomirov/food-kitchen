package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import com.example.foodkitchen.data.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<RecipeServiceModel> findAll(){
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public RecipeServiceModel findById(@PathVariable String id){
        return recipeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Recipe add(@RequestBody Recipe recipe, @AuthenticationPrincipal User principal){
        return recipeService.add(recipe, principal.getUsername());
    }

    @PatchMapping
    public RecipeServiceModel update(@RequestBody RecipeServiceModel model,
                                     @AuthenticationPrincipal User principal){
        return recipeService.updateRating(model, principal);
    }
}
