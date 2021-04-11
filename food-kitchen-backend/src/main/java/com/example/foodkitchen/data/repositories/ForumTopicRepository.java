package com.example.foodkitchen.data.repositories;

import com.example.foodkitchen.data.entities.ForumTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumTopicRepository extends JpaRepository<ForumTopic, String> {
}
