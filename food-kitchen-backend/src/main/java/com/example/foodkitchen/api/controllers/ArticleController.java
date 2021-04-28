package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.api.payroll.ArticleModelAssembler;
import com.example.foodkitchen.data.entities.Article;
import com.example.foodkitchen.data.models.service.ArticleServiceModel;
import com.example.foodkitchen.data.services.ArticleService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleModelAssembler articleModelAssembler;

    public ArticleController(ArticleService articleService, ArticleModelAssembler articleModelAssembler) {
        this.articleService = articleService;
        this.articleModelAssembler = articleModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ArticleServiceModel>>> findAll(){

        List<EntityModel<ArticleServiceModel>> articles = articleService.findAll()
                .stream()
                .map(articleModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(articles,
                linkTo(methodOn(ArticleController.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ArticleServiceModel>> findById(@PathVariable String id){
        return ResponseEntity.ok(articleModelAssembler.toModel(articleService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EntityModel<ArticleServiceModel>> add(@RequestBody ArticleServiceModel model){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleModelAssembler.toModel(articleService.add(model)));
    }
}
