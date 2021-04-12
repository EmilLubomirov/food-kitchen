package com.example.foodkitchen.data.repositories;

import com.example.foodkitchen.data.entities.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, String> {

    ForumCategory findByTitle(String title);
}
