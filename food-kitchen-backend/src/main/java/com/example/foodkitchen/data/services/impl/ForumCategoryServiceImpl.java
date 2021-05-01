package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.ForumCategory;
import com.example.foodkitchen.data.models.service.ForumCategoryServiceModel;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import com.example.foodkitchen.data.repositories.ForumCategoryRepository;
import com.example.foodkitchen.data.services.ForumCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForumCategoryServiceImpl implements ForumCategoryService {

    private final ForumCategoryRepository forumCategoryRepository;
    private final ModelMapper modelMapper;

    public ForumCategoryServiceImpl(ForumCategoryRepository forumCategoryRepository, ModelMapper modelMapper) {
        this.forumCategoryRepository = forumCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ForumCategoryServiceModel> findAll() {

        return forumCategoryRepository.findAll()
                .stream()
                .map(c -> {
                    ForumCategoryServiceModel model = modelMapper.map(c, ForumCategoryServiceModel.class);

                    Set<ForumTopicServiceModel> sortedTopics = model.getTopics()
                            .stream()
                            .sorted(Comparator.comparing(ForumTopicServiceModel::getDate))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

                    model.setTopics(sortedTopics);

                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ForumCategoryServiceModel findByTitle(String title) {

        ForumCategory category = forumCategoryRepository.findByTitle(title);

        if (category == null){
            return null;
        }

        return modelMapper.map(category, ForumCategoryServiceModel.class);
    }

    @Override
    public void seedForumCategoriesInDB() {
        forumCategoryRepository.saveAndFlush(new ForumCategory("How it's made"));
        forumCategoryRepository.saveAndFlush(new ForumCategory("Useful information and best practices"));
        forumCategoryRepository.saveAndFlush(new ForumCategory("I need help!"));
        forumCategoryRepository.saveAndFlush(new ForumCategory("Culinary events"));
        forumCategoryRepository.saveAndFlush(new ForumCategory("Other"));
    }

    @Override
    public long count() {
        return forumCategoryRepository.count();
    }
}
