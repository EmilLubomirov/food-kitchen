package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.Article;
import com.example.foodkitchen.data.models.service.ArticleServiceModel;
import com.example.foodkitchen.data.repositories.ArticleRepository;
import com.example.foodkitchen.data.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArticleServiceModel> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleServiceModel add(ArticleServiceModel article) {

        Article createArticle = new Article(article.getTitle(),
                                            article.getDescription(),
                                            article.getImageUrl());

        return modelMapper.map(articleRepository.saveAndFlush(createArticle),
                                ArticleServiceModel.class);
    }

    @Override
    public ArticleServiceModel findById(String id) {

        Article found = articleRepository.findById(id).orElse(null);

        if (found == null){
            return null;
        }

        return modelMapper.map(found, ArticleServiceModel.class);
    }
}
