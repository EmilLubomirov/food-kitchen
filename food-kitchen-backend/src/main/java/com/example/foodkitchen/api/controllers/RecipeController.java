package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.api.payroll.RecipeModelAssembler;
import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.recipe.RecipeFilterModel;
import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import com.example.foodkitchen.data.services.FoodCategoryService;
import com.example.foodkitchen.data.services.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final FoodCategoryService foodCategoryService;
    private final RecipeModelAssembler recipeModelAssembler;

    public RecipeController(RecipeService recipeService, FoodCategoryService foodCategoryService, RecipeModelAssembler recipeModelAssembler) {
        this.recipeService = recipeService;
        this.foodCategoryService = foodCategoryService;
        this.recipeModelAssembler = recipeModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<RecipeServiceModel>>> findAll(){

        List<EntityModel<RecipeServiceModel>> recipes = recipeService.findAll()
                .stream()
                .map(recipeModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(recipes,
                linkTo(methodOn(RecipeController.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecipeServiceModel>> findById(@PathVariable String id){
        return ResponseEntity.ok(recipeModelAssembler.toModel(recipeService.findById(id)));
    }

    @GetMapping("/category")
    public ResponseEntity<CollectionModel<EntityModel<FoodCategoryServiceModel>>> findAllCategories(){

        if (foodCategoryService.count() == 0){
            foodCategoryService.seedFoodCategoriesInDB();
        }

        List<EntityModel<FoodCategoryServiceModel>> categories = foodCategoryService.findAll()
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(categories,
                linkTo(methodOn(RecipeController.class).findAllCategories()).withSelfRel()));
    }

    @GetMapping("/favorite")
    public ResponseEntity<CollectionModel<EntityModel<RecipeServiceModel>>> findUserFavorites(@AuthenticationPrincipal User principal){

        List<EntityModel<RecipeServiceModel>> favorites = recipeService.findFavoriteRecipes(principal.getUsername())
                .stream()
                .map(recipeModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(favorites,
                linkTo(methodOn(RecipeController.class).findUserFavorites(principal)).withSelfRel()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<RecipeServiceModel>> add(@RequestBody Recipe recipe,
                                                               @AuthenticationPrincipal User principal){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeModelAssembler.toModel(
                recipeService.add(recipe, principal.getUsername())));
    }

    @PostMapping("/filter")
    public ResponseEntity<CollectionModel<EntityModel<RecipeServiceModel>>> filterByCategory(
           @Valid @RequestBody RecipeFilterModel recipe){

        List<EntityModel<RecipeServiceModel>> filtered = recipeService.findByCategories(recipe)
                .stream()
                .map(recipeModelAssembler::toModel)
                .limit(recipe.getLimit())
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(filtered,
                linkTo(methodOn(RecipeController.class).filterByCategory(recipe)).withSelfRel()));
    }

    @PatchMapping
    public ResponseEntity<EntityModel<RecipeServiceModel>> update(@RequestBody RecipeServiceModel recipe,
                                     @AuthenticationPrincipal User principal){

        return ResponseEntity.ok(recipeModelAssembler.toModel(
                recipeService.updateRating(recipe, principal)));
    }

    @PatchMapping("/addToFavorites")
    public ResponseEntity<EntityModel<RecipeServiceModel>> addToFavorites(@RequestBody RecipeServiceModel recipe,
                                             @AuthenticationPrincipal User principal){

        return ResponseEntity.ok(recipeModelAssembler.toModel(
                recipeService.addToFavorites(recipe, principal)));
    }

    @DeleteMapping("/deleteFromFav")
    public ResponseEntity<EntityModel<RecipeServiceModel>> deleteFromFavorites(@RequestParam String id,
                                                                               @AuthenticationPrincipal User principal){
        return ResponseEntity.ok(recipeModelAssembler.toModel(
                recipeService.deleteFromFavorites(id, principal.getUsername())));
    }

}
