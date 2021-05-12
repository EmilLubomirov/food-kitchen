package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.FoodCategory;
import com.example.foodkitchen.data.models.service.FoodCategoryServiceModel;
import com.example.foodkitchen.data.repositories.FoodCategoryRepository;
import com.example.foodkitchen.data.services.FoodCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"food-categories"})
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ModelMapper modelMapper;

    public FoodCategoryServiceImpl(FoodCategoryRepository foodCategoryRepository, ModelMapper modelMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable
    public List<FoodCategoryServiceModel> findAll() {
        return foodCategoryRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, FoodCategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void seedFoodCategoriesInDB() {
        foodCategoryRepository.saveAndFlush(new FoodCategory("Starters"));
        foodCategoryRepository.saveAndFlush(new FoodCategory("Main dishes"));
        foodCategoryRepository.saveAndFlush(new FoodCategory("Desserts"));
        foodCategoryRepository.saveAndFlush(new FoodCategory("Drinks"));
    }

    @Override
    public long count() {
        return foodCategoryRepository.count();
    }
}
