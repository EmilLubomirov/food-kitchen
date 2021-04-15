package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.entities.ForumTopic;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.ForumCommentServiceModel;

import java.util.List;

public interface ForumCommentService {

    ForumCommentServiceModel add(String content, ForumTopic topic, User user);

    ForumCommentServiceModel add(String content, String topicId, User user);
}
