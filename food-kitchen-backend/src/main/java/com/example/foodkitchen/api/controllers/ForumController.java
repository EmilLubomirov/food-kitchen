package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;
import com.example.foodkitchen.data.services.ForumCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumCategoryService forumCategoryService;

    public ForumController(ForumCategoryService forumCategoryService) {
        this.forumCategoryService = forumCategoryService;
    }

    @GetMapping
    public List<ForumCategoryServiceModel> findCategories(){

        if (forumCategoryService.count() == 0){
            forumCategoryService.seedForumCategoriesInDB();
        }

        return forumCategoryService.findAll();
    }
}
