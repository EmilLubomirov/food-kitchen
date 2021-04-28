package com.example.foodkitchen.data.models.view;

import com.example.foodkitchen.data.models.service.ForumTopicServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumCategoryViewModel {

    private String id;
    private String title;
    private CollectionModel<EntityModel<ForumTopicServiceModel>> topics;
}
