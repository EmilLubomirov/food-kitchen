package com.example.foodkitchen.data.repositories;

import com.example.foodkitchen.data.entities.ForumComment;
import com.example.foodkitchen.data.entities.ForumTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumCommentRepository extends JpaRepository<ForumComment, String> {

    List<ForumComment> findAllByTopic(ForumTopic topic);
}
