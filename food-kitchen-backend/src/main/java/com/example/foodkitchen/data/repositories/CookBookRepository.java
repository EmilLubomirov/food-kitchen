package com.example.foodkitchen.data.repositories;

import com.example.foodkitchen.data.entities.CookBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookBookRepository extends JpaRepository<CookBook, String> {
}
