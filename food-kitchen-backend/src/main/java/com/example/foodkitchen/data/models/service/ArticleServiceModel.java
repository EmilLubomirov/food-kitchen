package com.example.foodkitchen.data.models.service;

import com.example.foodkitchen.data.entities.base.BaseInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleServiceModel extends BaseInfoEntity {

    private String imageUrl;
    public Date publishedOn;
}
