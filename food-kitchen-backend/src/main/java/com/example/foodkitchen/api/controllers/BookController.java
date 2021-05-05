package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.api.payroll.BookModelAssembler;
import com.example.foodkitchen.data.models.service.CookBookServiceModel;
import com.example.foodkitchen.data.services.CookBookService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final CookBookService cookBookService;
    private final BookModelAssembler bookModelAssembler;

    public BookController(CookBookService cookBookService, BookModelAssembler bookModelAssembler) {
        this.cookBookService = cookBookService;
        this.bookModelAssembler = bookModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CookBookServiceModel>>> findAll(){

        List<EntityModel<CookBookServiceModel>> books = cookBookService.findAll()
                .stream()
                .map(bookModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(books,
                linkTo(methodOn(BookController.class).findAll()).withSelfRel()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EntityModel<CookBookServiceModel>> add(@RequestBody CookBookServiceModel model){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookModelAssembler.toModel(cookBookService.add(model)));
    }
}
