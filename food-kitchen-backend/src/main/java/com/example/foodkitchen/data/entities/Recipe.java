package com.example.foodkitchen.data.entities;

import com.example.foodkitchen.data.entities.base.BaseEntity;
import com.example.foodkitchen.data.entities.base.BaseInfoEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe extends BaseInfoEntity {

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recipes_categories",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<FoodCategory> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recipes_voters",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "voter_id", referencedColumnName = "id"))
    private Set<User> voters;

    @Min(value = 0, message = "Rating should not be below 0")
    @Max(value = 5, message = "Rating should not be greater than 5")
    @Column(name = "rating", columnDefinition = "decimal(3, 2) default 0")
    private double rating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recipes_fans",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fan_id", referencedColumnName = "id"))
    private Set<User> fans;
}
