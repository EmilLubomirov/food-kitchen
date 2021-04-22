package com.example.foodkitchen.data.models.binding.recipe;

import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFilterModel {

    private Set<FoodCategoryServiceModel> categories;
}
