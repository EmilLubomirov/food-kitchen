package com.example.foodkitchen.data.models.binding.forumComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumCommentCreateModel {

    @NotBlank(message = "Content is mandatory")
    private String content;
}
