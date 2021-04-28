package com.example.foodkitchen.api.payroll;

import com.example.foodkitchen.api.controllers.BookController;
import com.example.foodkitchen.data.models.service.CookBookServiceModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<CookBookServiceModel,
        EntityModel<CookBookServiceModel>> {

    @Override
    public EntityModel<CookBookServiceModel> toModel(CookBookServiceModel book) {
        return EntityModel.of(book,
                linkTo(methodOn(BookController.class).findAll()).withRel("cookBooks"),
                linkTo(methodOn(BookController.class).add(book)).withRel("add"));
    }
}
