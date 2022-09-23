package kz.bitlab.bootcamp.finalproject.mapper;

import kz.bitlab.bootcamp.finalproject.dto.PostDto;
import kz.bitlab.bootcamp.finalproject.models.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toDto(Post post);
    Post toEntity(PostDto postDto);

    List<PostDto> toDtoList(List<Post> posts);
    List<Post> toEntityList(List<PostDto> postDtoList);
}
