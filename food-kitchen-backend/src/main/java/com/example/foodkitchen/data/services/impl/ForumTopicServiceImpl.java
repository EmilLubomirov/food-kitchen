package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.ForumCategory;
import com.example.foodkitchen.data.entities.ForumTopic;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.forumTopic.ForumTopicCreateModel;
import com.example.foodkitchen.data.models.service.ForumCommentServiceModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.repositories.ForumTopicRepository;
import com.example.foodkitchen.data.services.ForumCategoryService;
import com.example.foodkitchen.data.services.ForumCommentService;
import com.example.foodkitchen.data.services.ForumTopicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForumTopicServiceImpl implements ForumTopicService {

    private final ForumTopicRepository forumTopicRepository;
    private final ForumCategoryService forumCategoryService;
    private final ForumCommentService forumCommentService;
    private final ModelMapper modelMapper;

    public ForumTopicServiceImpl(ForumTopicRepository forumTopicRepository, ForumCategoryService forumCategoryService, ForumCommentService forumCommentService, ModelMapper modelMapper) {
        this.forumTopicRepository = forumTopicRepository;
        this.forumCategoryService = forumCategoryService;
        this.forumCommentService = forumCommentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ForumTopicServiceModel> findAll() {
        return forumTopicRepository.findAll()
                .stream()
                .map(t -> modelMapper.map(t, ForumTopicServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ForumTopicServiceModel add(ForumTopicCreateModel topicCreateModel, User principal) {

        ForumTopic topic = new ForumTopic();
        topic.setTitle(topicCreateModel.getTopicName());

        ForumCategory category = modelMapper.map
                (forumCategoryService.findByTitle(topicCreateModel.getCategoryName()), ForumCategory.class);

        topic.setCategory(category);

        ForumTopic createdTopic = forumTopicRepository.saveAndFlush(topic);
        forumCommentService.add(topicCreateModel.getQuestion(), topic, principal);

        return modelMapper.map(createdTopic, ForumTopicServiceModel.class);
    }

    @Override
    public ForumTopicServiceModel findById(String id) {

        ForumTopic topic = forumTopicRepository.findById(id).orElse(null);

        if (topic == null){
            return null;
        }

        ForumTopicServiceModel model = modelMapper.map(topic, ForumTopicServiceModel.class);

        Set<ForumCommentServiceModel> sortedComments = model.getComments()
                .stream()
                .sorted(Comparator.comparing(ForumCommentServiceModel::getDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        model.setComments(sortedComments);

        return model;
    }
}
