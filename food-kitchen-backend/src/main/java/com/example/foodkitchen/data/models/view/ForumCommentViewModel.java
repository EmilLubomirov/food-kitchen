package com.example.foodkitchen.data.models.view;

import com.example.foodkitchen.data.models.service.UserServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumCommentViewModel {

    private String content;
    private Date date;
    private UserServiceModel initiator;
}
