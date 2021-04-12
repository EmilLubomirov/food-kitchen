package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.ForumCategory;
import com.example.foodkitchen.data.entities.ForumTopic;
import com.example.foodkitchen.data.models.binding.forumTopic.ForumTopicCreateModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.repositories.ForumTopicRepository;
import com.example.foodkitchen.data.services.ForumCategoryService;
import com.example.foodkitchen.data.services.ForumTopicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumTopicServiceImpl implements ForumTopicService {

    private final ForumTopicRepository forumTopicRepository;
    private final ForumCategoryService forumCategoryService;
    private final ModelMapper modelMapper;

    public ForumTopicServiceImpl(ForumTopicRepository forumTopicRepository, ForumCategoryService forumCategoryService, ModelMapper modelMapper) {
        this.forumTopicRepository = forumTopicRepository;
        this.forumCategoryService = forumCategoryService;
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
    public ForumTopicServiceModel add(ForumTopicCreateModel topicCreateModel) {

        ForumTopic topic = new ForumTopic();
        topic.setTitle(topicCreateModel.getTopicName());

        ForumCategory category = modelMapper.map
                (forumCategoryService.findByTitle(topicCreateModel.getCategoryName()), ForumCategory.class);

        topic.setCategory(category);
        return modelMapper.map(forumTopicRepository.saveAndFlush(topic), ForumTopicServiceModel.class);
    }
}
