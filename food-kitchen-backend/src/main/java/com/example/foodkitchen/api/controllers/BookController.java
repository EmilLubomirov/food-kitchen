package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.models.service.CookBookServiceModel;
import com.example.foodkitchen.data.services.CookBookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final CookBookService cookBookService;

    public BookController(CookBookService cookBookService) {
        this.cookBookService = cookBookService;
    }

    @GetMapping
    public List<CookBookServiceModel> findAll(){
        return cookBookService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CookBookServiceModel add(@RequestBody CookBookServiceModel model){
        System.out.println();
        return cookBookService.add(model);
    }
}
