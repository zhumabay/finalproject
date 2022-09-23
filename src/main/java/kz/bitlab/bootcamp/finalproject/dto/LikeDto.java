package kz.bitlab.bootcamp.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDto {

    private Long id;
    private UserDto user;
    private PostDto post;
}
