package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.data.models.service.ArticleServiceModel;
import com.example.foodkitchen.data.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleServiceModel> findAll(){
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public ArticleServiceModel findById(@PathVariable String id){
        return articleService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleServiceModel add(@RequestBody ArticleServiceModel model){
        return articleService.add(model);
    }
}
