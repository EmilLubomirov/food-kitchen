package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.ForumComment;
import com.example.foodkitchen.data.entities.ForumTopic;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.ForumCommentServiceModel;
import com.example.foodkitchen.data.repositories.ForumCommentRepository;
import com.example.foodkitchen.data.repositories.ForumTopicRepository;
import com.example.foodkitchen.data.services.ForumCommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ForumCommentServiceImpl implements ForumCommentService {

    private final ForumCommentRepository forumCommentRepository;
    private final ForumTopicRepository forumTopicRepository;
    private final ModelMapper modelMapper;

    public ForumCommentServiceImpl(ForumCommentRepository forumCommentRepository, ForumTopicRepository forumTopicRepository, ModelMapper modelMapper) {
        this.forumCommentRepository = forumCommentRepository;
        this.forumTopicRepository = forumTopicRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ForumCommentServiceModel add(String content, ForumTopic topic, User user) {

        ForumComment comment = new ForumComment(content, user, topic);
        return modelMapper.map(forumCommentRepository.saveAndFlush(comment), ForumCommentServiceModel.class);
    }

    @Override
    public ForumCommentServiceModel add(String content, String topicId, User user) {

        ForumTopic topic = forumTopicRepository.findById(topicId).orElse(null);

        if (topic == null){
            return null;
        }

        ForumComment comment = new ForumComment(content, user, topic);
        return modelMapper.map(forumCommentRepository.saveAndFlush(comment), ForumCommentServiceModel.class);
    }
}
