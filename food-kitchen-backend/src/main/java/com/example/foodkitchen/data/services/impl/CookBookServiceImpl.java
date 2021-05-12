package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.CookBook;
import com.example.foodkitchen.data.models.service.CookBookServiceModel;
import com.example.foodkitchen.data.repositories.CookBookRepository;
import com.example.foodkitchen.data.services.CookBookService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"cook-books"})
public class CookBookServiceImpl implements CookBookService {

    private final CookBookRepository cookBookRepository;
    private final ModelMapper modelMapper;

    public CookBookServiceImpl(CookBookRepository cookBookRepository, ModelMapper modelMapper) {
        this.cookBookRepository = cookBookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable
    public List<CookBookServiceModel> findAll() {
        return cookBookRepository.findAll()
                .stream()
                .map(b -> modelMapper.map(b, CookBookServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(allEntries = true)
    public CookBookServiceModel add(CookBookServiceModel cookBook) {

        CookBook book = modelMapper.map(cookBook, CookBook.class);
        return modelMapper.map(cookBookRepository.save(book), CookBookServiceModel.class);
    }
}
