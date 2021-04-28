package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.api.payroll.ForumCategoryModelAssembler;
import com.example.foodkitchen.api.payroll.ForumTopicModelAssembler;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.forumComment.ForumCommentCreateModel;
import com.example.foodkitchen.data.models.binding.forumTopic.ForumTopicCreateModel;
import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.models.view.ForumCategoryViewModel;
import com.example.foodkitchen.data.services.ForumCategoryService;
import com.example.foodkitchen.data.services.ForumCommentService;
import com.example.foodkitchen.data.services.ForumTopicService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    private final ForumCategoryService forumCategoryService;
    private final ForumTopicService forumTopicService;
    private final ForumCommentService forumCommentService;
    private final ModelMapper modelMapper;

    private final ForumCategoryModelAssembler forumCategoryModelAssembler;
    private final ForumTopicModelAssembler forumTopicModelAssembler;

    public ForumController(ForumCategoryService forumCategoryService, ForumTopicService forumTopicService, ForumCommentService forumCommentService, ModelMapper modelMapper, ForumCategoryModelAssembler forumCategoryModelAssembler, ForumTopicModelAssembler forumTopicModelAssembler) {
        this.forumCategoryService = forumCategoryService;
        this.forumTopicService = forumTopicService;
        this.forumCommentService = forumCommentService;
        this.modelMapper = modelMapper;
        this.forumCategoryModelAssembler = forumCategoryModelAssembler;
        this.forumTopicModelAssembler = forumTopicModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<ForumCategoryViewModel>> findCategories(){

        if (forumCategoryService.count() == 0){
            forumCategoryService.seedForumCategoriesInDB();
        }

        List<ForumCategoryServiceModel> all = forumCategoryService.findAll();

        List<ForumCategoryViewModel> collect1 = all
                .stream()
                .map(c -> modelMapper.map(c, ForumCategoryViewModel.class))
                .collect(Collectors.toList());

        IntStream.range(0, collect1.size())
                .forEach(index ->
                        collect1.get(index).setTopics(
                                forumTopicModelAssembler.toCollectionModel(all.get(index).getTopics()))
                        );

        List<EntityModel<ForumCategoryViewModel>> collect = collect1.stream()
                .map(forumCategoryModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(collect,
                linkTo(methodOn(ForumController.class).findCategories()).withRel("categories"));
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<EntityModel<ForumTopicServiceModel>> findTopicById(@PathVariable String topicId){
        return ResponseEntity.ok(forumTopicModelAssembler.toModel(forumTopicService.findById(topicId)));
    }

    @PostMapping("/addTopic")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<EntityModel<ForumTopicServiceModel>> addTopic(@RequestBody ForumTopicCreateModel topicCreateModel,
                                           @AuthenticationPrincipal User principal){
        return ResponseEntity.ok(forumTopicModelAssembler.toModel(forumTopicService.add(topicCreateModel, principal)));
    }

    @PostMapping("/{topicId}/addComment")
    public ResponseEntity<EntityModel<ForumTopicServiceModel>> addComment(@RequestBody ForumCommentCreateModel model,
                                               @PathVariable String topicId,
                                               @AuthenticationPrincipal User principal){

        forumCommentService.add(model.getContent(), topicId, principal);
        return ResponseEntity.ok(forumTopicModelAssembler.toModel(forumTopicService.findById(topicId)));
    }
}
