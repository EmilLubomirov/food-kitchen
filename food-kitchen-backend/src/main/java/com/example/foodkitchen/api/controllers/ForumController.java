package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.forumComment.ForumCommentCreateModel;
import com.example.foodkitchen.data.models.binding.forumTopic.ForumTopicCreateModel;
import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;
import com.example.foodkitchen.data.models.service.ForumCommentServiceModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.services.ForumCategoryService;
import com.example.foodkitchen.data.services.ForumCommentService;
import com.example.foodkitchen.data.services.ForumTopicService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumCategoryService forumCategoryService;
    private final ForumTopicService forumTopicService;
    private final ForumCommentService forumCommentService;

    public ForumController(ForumCategoryService forumCategoryService, ForumTopicService forumTopicService, ForumCommentService forumCommentService) {
        this.forumCategoryService = forumCategoryService;
        this.forumTopicService = forumTopicService;
        this.forumCommentService = forumCommentService;
    }

    @GetMapping
    public List<ForumCategoryServiceModel> findCategories(){

        if (forumCategoryService.count() == 0){
            forumCategoryService.seedForumCategoriesInDB();
        }

        return forumCategoryService.findAll();
    }

    @GetMapping("/{topicId}")
    public ForumTopicServiceModel findTopicById(@PathVariable String topicId){
        return forumTopicService.findById(topicId);
    }

    @PostMapping("/addTopic")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ForumTopicServiceModel addTopic(@RequestBody ForumTopicCreateModel topicCreateModel,
                                           @AuthenticationPrincipal User principal){
        return forumTopicService.add(topicCreateModel, principal);
    }

    @PostMapping("/{topicId}/addComment")
    public ForumCommentServiceModel addComment(@RequestBody ForumCommentCreateModel model,
                                               @PathVariable String topicId,
                                               @AuthenticationPrincipal User principal){
        return forumCommentService.add(model.getContent(), topicId, principal);
    }
}
