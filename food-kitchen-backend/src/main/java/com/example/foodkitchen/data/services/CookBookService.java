package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.CookBookServiceModel;

import java.util.List;

public interface CookBookService {

    List<CookBookServiceModel> findAll();

    CookBookServiceModel add(CookBookServiceModel cookBook);
}
