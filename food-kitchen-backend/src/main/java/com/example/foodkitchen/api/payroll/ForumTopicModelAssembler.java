package com.example.foodkitchen.api.payroll;

import com.example.foodkitchen.api.controllers.ForumController;
import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ForumTopicModelAssembler implements RepresentationModelAssembler<ForumTopicServiceModel,
        EntityModel<ForumTopicServiceModel>> {


    @Override
    public EntityModel<ForumTopicServiceModel> toModel(ForumTopicServiceModel topic) {
        return EntityModel.of(topic,
                linkTo(methodOn(ForumController.class).findTopicById(topic.getId())).withSelfRel(),
                linkTo(methodOn(ForumController.class).addTopic(null, null))
                        .withRel("addTopic"),
                linkTo(methodOn(ForumController.class).addComment(null, topic.getId(), null))
                        .withRel("addComment"));
    }

    public CollectionModel<EntityModel<ForumTopicServiceModel>> toCollectionModel(Set<ForumTopicServiceModel> topics) {

        List<EntityModel<ForumTopicServiceModel>> collect = topics
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(collect);
    }
}
