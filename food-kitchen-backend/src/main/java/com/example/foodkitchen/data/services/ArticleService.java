package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.ArticleServiceModel;

import java.util.List;

public interface ArticleService {

    List<ArticleServiceModel> findAll();

    ArticleServiceModel add(ArticleServiceModel article);

    ArticleServiceModel findById(String id);
}
