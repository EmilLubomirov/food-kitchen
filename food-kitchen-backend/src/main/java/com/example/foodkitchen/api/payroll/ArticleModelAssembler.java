package com.example.foodkitchen.api.payroll;

import com.example.foodkitchen.api.controllers.ArticleController;
import com.example.foodkitchen.data.models.service.ArticleServiceModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticleModelAssembler implements RepresentationModelAssembler<ArticleServiceModel,
        EntityModel<ArticleServiceModel>> {


    @Override
    public EntityModel<ArticleServiceModel> toModel(ArticleServiceModel article) {
        return EntityModel.of(article,
                linkTo(methodOn(ArticleController.class).findAll()).withRel("articles"),
                linkTo(methodOn(ArticleController.class).findById(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticleController.class).add(article)).withRel("add"));
    }
}
