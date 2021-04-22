package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.recipe.RecipeFilterModel;
import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import com.example.foodkitchen.data.services.FoodCategoryService;
import com.example.foodkitchen.data.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final FoodCategoryService foodCategoryService;

    public RecipeController(RecipeService recipeService, FoodCategoryService foodCategoryService) {
        this.recipeService = recipeService;
        this.foodCategoryService = foodCategoryService;
    }

    @GetMapping
    public List<RecipeServiceModel> findAll(){
        return recipeService.findAll();
    }

//    @GetMapping("/filter")
//    public List<RecipeServiceModel> findByCategories(@RequestParam("categories")
//                                                                 List<FoodCategoryServiceModel> categories){
//        return null;
//    }

    @GetMapping("/{id}")
    public RecipeServiceModel findById(@PathVariable String id){
        return recipeService.findById(id);
    }

    @GetMapping("/category")
    public List<FoodCategoryServiceModel> findAllCategories(){

        if (foodCategoryService.count() == 0){
            foodCategoryService.seedFoodCategoriesInDB();
        }

        return foodCategoryService.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Recipe add(@RequestBody Recipe recipe, @AuthenticationPrincipal User principal){
        return recipeService.add(recipe, principal.getUsername());
    }

    @PostMapping("/filter")
    public List<RecipeServiceModel> filterByCategory(@RequestBody RecipeFilterModel recipe){
        return recipeService.findByCategories(recipe);
    }

    @PatchMapping
    public RecipeServiceModel update(@RequestBody RecipeServiceModel model,
                                     @AuthenticationPrincipal User principal){
        return recipeService.updateRating(model, principal);
    }
}
