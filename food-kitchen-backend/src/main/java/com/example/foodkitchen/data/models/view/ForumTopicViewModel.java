package com.example.foodkitchen.data.models.view;

import com.example.foodkitchen.data.models.service.ForumCommentServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumTopicViewModel {

    private String id;
    private String title;
    private Date date;
    private Set<ForumCommentServiceModel> comments;
}
