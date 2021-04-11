package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.repositories.ForumTopicRepository;
import com.example.foodkitchen.data.services.ForumTopicService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumTopicServiceImpl implements ForumTopicService {

    private final ForumTopicRepository forumTopicRepository;
    private final ModelMapper modelMapper;

    public ForumTopicServiceImpl(ForumTopicRepository forumTopicRepository, ModelMapper modelMapper) {
        this.forumTopicRepository = forumTopicRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ForumTopicServiceModel> findAll() {
        return forumTopicRepository.findAll()
                .stream()
                .map(t -> modelMapper.map(t, ForumTopicServiceModel.class))
                .collect(Collectors.toList());
    }
}
