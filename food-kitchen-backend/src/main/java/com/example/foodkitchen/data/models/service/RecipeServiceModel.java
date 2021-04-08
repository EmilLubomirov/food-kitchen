package com.example.foodkitchen.data.models.service;

import com.example.foodkitchen.data.entities.base.BaseInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeServiceModel extends BaseInfoEntity {

    private String imageUrl;
    private String publisher;
}
