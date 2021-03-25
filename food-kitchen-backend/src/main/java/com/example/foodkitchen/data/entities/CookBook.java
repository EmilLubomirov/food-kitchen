package com.example.foodkitchen.data.entities;

import com.example.foodkitchen.data.entities.base.BaseInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "cook_books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CookBook extends BaseInfoEntity {

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(mappedBy = "cookBooks")
    private Set<User> owners;
}
