package com.example.foodkitchen.data.repositories;

import com.example.foodkitchen.data.entities.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, String> {

    FoodCategory findByName(String name);
}
