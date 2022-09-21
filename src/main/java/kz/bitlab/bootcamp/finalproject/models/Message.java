package kz.bitlab.bootcamp.finalproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message implements Comparable<Message>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @PrePersist
    private void prePersist(){
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public int compareTo(Message message) {
        return this.getId().compareTo(message.getId());
    }
}
