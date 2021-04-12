package com.example.foodkitchen.data.models.binding.forumTopic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumTopicCreateModel {

    private String categoryName;
    private String topicName;
    private String question;
}
