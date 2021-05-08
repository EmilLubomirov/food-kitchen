package com.example.foodkitchen.api.payroll;

import com.example.foodkitchen.api.controllers.UserController;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserServiceModel,
        EntityModel<UserServiceModel>> {

    private final User principal = new User("demo");

    @Override
    public EntityModel<UserServiceModel> toModel(UserServiceModel user) {

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).create(
                        null)).withRel("register"),
                linkTo(methodOn(UserController.class).login(principal)).withRel("login"),
                linkTo(methodOn(UserController.class).verifyLogin("authToken")).withRel("verifyLogin"),
                linkTo(methodOn(UserController.class).updateUsername("newUsername", principal))
                        .withRel("updateUsername"),
                linkTo(methodOn(UserController.class).updatePassword("oldPassword","newPassword", principal))
                        .withRel("updatePassword"),
                linkTo(methodOn(UserController.class).updateProfilePicture("imgUrl", principal))
                        .withRel("updateProfilePicture")
                );
    }
}
