package kz.bitlab.bootcamp.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentDto {

    private Long id;
    private String text;
    private Timestamp createdAt;
    private UserDto user;
    private PostDto post;
}
