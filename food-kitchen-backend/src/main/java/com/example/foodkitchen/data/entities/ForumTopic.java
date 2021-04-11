package com.example.foodkitchen.data.entities;

import com.example.foodkitchen.data.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "forum_topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumTopic extends BaseEntity {

    private String title;

    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ForumComment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private ForumCategory category;
}
