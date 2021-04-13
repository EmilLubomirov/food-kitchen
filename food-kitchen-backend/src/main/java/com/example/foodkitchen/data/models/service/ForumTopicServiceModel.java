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
public class ForumTopicServiceModel {

    private String id;
    private String title;
    private Set<ForumCommentServiceModel> comments;
}
