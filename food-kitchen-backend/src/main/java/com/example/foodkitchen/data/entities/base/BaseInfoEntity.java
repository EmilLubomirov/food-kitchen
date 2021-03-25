package com.example.foodkitchen.data.entities.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseInfoEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;
}
