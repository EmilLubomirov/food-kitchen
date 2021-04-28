package com.example.foodkitchen.api.payroll;

import com.example.foodkitchen.api.controllers.RecipeController;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.RecipeServiceModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecipeModelAssembler implements RepresentationModelAssembler<RecipeServiceModel,
        EntityModel<RecipeServiceModel>> {

    private final User user = new User("demo");

    @Override
    public EntityModel<RecipeServiceModel> toModel(RecipeServiceModel recipe) {

        return EntityModel.of(recipe,
                linkTo(methodOn(RecipeController.class).findById(recipe.getId())).withSelfRel(),
                linkTo(methodOn(RecipeController.class).findAll()).withRel("recipes"),
                linkTo(methodOn(RecipeController.class).findAllCategories()).withRel("recipeCategories"),
                linkTo(methodOn(RecipeController.class).findUserFavorites(user)).withRel("userFavorites"),
                linkTo(methodOn(RecipeController.class).filterByCategory(null)).withRel("filterByCategory"),
                linkTo(methodOn(RecipeController.class).update(recipe, user)).withRel("update"),
                linkTo(methodOn(RecipeController.class).addToFavorites(recipe, user)).withRel("addToFavorites"),
                linkTo(methodOn(RecipeController.class).deleteFromFavorites(recipe.getId(), user)).withRel("deleteFromFavorites"));
    }
}
