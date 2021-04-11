package com.example.foodkitchen.data.models.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumCategoryServiceModel {

    private String title;
    private Set<ForumTopicServiceModel> topics;
}
