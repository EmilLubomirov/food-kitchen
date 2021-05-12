package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.Article;
import com.example.foodkitchen.data.models.service.ArticleServiceModel;
import com.example.foodkitchen.data.repositories.ArticleRepository;
import com.example.foodkitchen.data.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"articles"})
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable
    public List<ArticleServiceModel> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(allEntries = true)
    public ArticleServiceModel add(ArticleServiceModel article) {

        Article createArticle = new Article(article.getTitle(),
                                            article.getDescription(),
                                            article.getImageUrl());

        return modelMapper.map(articleRepository.saveAndFlush(createArticle),
                                ArticleServiceModel.class);
    }

    @Override
    @Cacheable
    public ArticleServiceModel findById(String id) {

        Article found = articleRepository.findById(id).orElse(null);

        if (found == null){
            return null;
        }

        return modelMapper.map(found, ArticleServiceModel.class);
    }
}
