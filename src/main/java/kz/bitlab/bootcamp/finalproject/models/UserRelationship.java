package kz.bitlab.bootcamp.finalproject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_relationships")
@Getter
@Setter
public class UserRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private User firstUser;

    @ManyToOne
    private User secondUser;

    @Column(name = "type")
    private String type;
}
