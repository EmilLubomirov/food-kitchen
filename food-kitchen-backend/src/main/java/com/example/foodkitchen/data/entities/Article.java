package com.example.foodkitchen.data.entities;

import com.example.foodkitchen.data.entities.base.BaseInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseInfoEntity {

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "published_on")
    @CreationTimestamp
    private Date publishedOn;

    public Article(String title, String description, String imageUrl) {
        super(title, description);
        this.imageUrl = imageUrl;
    }
}
