package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.models.binding.forumTopic.ForumTopicCreateModel;
import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.services.ForumCategoryService;
import com.example.foodkitchen.data.services.ForumTopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumCategoryService forumCategoryService;
    private final ForumTopicService forumTopicService;

    public ForumController(ForumCategoryService forumCategoryService, ForumTopicService forumTopicService) {
        this.forumCategoryService = forumCategoryService;
        this.forumTopicService = forumTopicService;
    }

    @GetMapping
    public List<ForumCategoryServiceModel> findCategories(){

        if (forumCategoryService.count() == 0){
            forumCategoryService.seedForumCategoriesInDB();
        }

        return forumCategoryService.findAll();
    }

    @PostMapping("/addTopic")
    public ForumTopicServiceModel addTopic(@RequestBody ForumTopicCreateModel topicCreateModel){
        return forumTopicService.add(topicCreateModel);
    }
}
