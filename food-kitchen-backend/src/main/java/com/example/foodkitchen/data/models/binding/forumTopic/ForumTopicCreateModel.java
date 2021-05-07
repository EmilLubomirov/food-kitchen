package com.example.foodkitchen.data.models.binding.forumTopic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumTopicCreateModel {

    @NotBlank(message = "Category name is mandatory")
    private String categoryName;

    @NotBlank(message = "Topic name is mandatory")
    private String topicName;

    @NotBlank(message = "Question is mandatory")
    private String question;
}
