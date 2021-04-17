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
public class CookBookServiceModel extends BaseInfoEntity {

    private String author;
    private int year;
    private String imageUrl;
}
