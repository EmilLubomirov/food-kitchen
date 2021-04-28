package com.example.foodkitchen.api.payroll;

import com.example.foodkitchen.api.controllers.ForumController;
import com.example.foodkitchen.data.models.view.ForumCategoryViewModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ForumCategoryModelAssembler implements RepresentationModelAssembler<ForumCategoryViewModel,
        EntityModel<ForumCategoryViewModel>>{

    @Override
    public EntityModel<ForumCategoryViewModel> toModel(ForumCategoryViewModel category) {
        return EntityModel.of(category,
                linkTo(methodOn(ForumController.class).findCategories()).withRel("categories"));
    }
}
