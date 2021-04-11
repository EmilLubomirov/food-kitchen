package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;

import java.util.List;

public interface ForumTopicService {

    List<ForumTopicServiceModel> findAll();
}
