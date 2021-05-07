package com.example.foodkitchen.data.entities;

import com.example.foodkitchen.data.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "forum_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumComment extends BaseEntity {

    @NotBlank(message = "Content is mandatory")
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonBackReference
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    @JsonBackReference
    private ForumTopic topic;

    @NotNull(message = "Date is mandatory")
    @CreationTimestamp
    private Date date;

    public ForumComment(String content, User initiator, ForumTopic topic){
        this.content = content;
        this.initiator = initiator;
        this.topic = topic;
    }
}
