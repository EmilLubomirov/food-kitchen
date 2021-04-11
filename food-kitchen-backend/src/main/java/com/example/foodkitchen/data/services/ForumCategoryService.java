package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;

import java.util.List;

public interface ForumCategoryService {

    List<ForumCategoryServiceModel> findAll();

    void seedForumCategoriesInDB();

    long count();
}
