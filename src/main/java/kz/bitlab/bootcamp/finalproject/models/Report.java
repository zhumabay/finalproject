package kz.bitlab.bootcamp.finalproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @Column(name = "handled")
    private boolean handled;

    @Column(name = "type")
    private String type;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    private void prePersist(){
        handled = false;
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
