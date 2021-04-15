package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.forumTopic.ForumTopicCreateModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;

import java.util.List;

public interface ForumTopicService {

    List<ForumTopicServiceModel> findAll();

    ForumTopicServiceModel add(ForumTopicCreateModel topicCreateModel, User principal);

    ForumTopicServiceModel findById(String id);
}
