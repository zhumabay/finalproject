package kz.bitlab.bootcamp.finalproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "comments_amount")
    private int commentsAmount;

    @Column(name = "likes_amount")
    private int likesAmount;

    @PrePersist
    private void prePersist(){
        commentsAmount = 0;
        likesAmount = 0;
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
