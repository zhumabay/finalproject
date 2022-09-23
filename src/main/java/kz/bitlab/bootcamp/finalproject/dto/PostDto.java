package kz.bitlab.bootcamp.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String text;
    private Timestamp createdAt;
    private UserDto user;
    private String imageUrl;
    private int commentsAmount;
    private int likesAmount;
}
