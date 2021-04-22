package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;

import java.util.List;

public interface FoodCategoryService {

    List<FoodCategoryServiceModel> findAll();

    void seedFoodCategoriesInDB();

    long count();
}
