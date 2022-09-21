package kz.bitlab.bootcamp.finalproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

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

    @ManyToOne
    private Post post;

    @PrePersist
    private void prePersist(){
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
