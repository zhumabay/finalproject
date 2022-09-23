package kz.bitlab.bootcamp.finalproject.mapper;

import kz.bitlab.bootcamp.finalproject.dto.CommentDto;
import kz.bitlab.bootcamp.finalproject.models.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);

    List<CommentDto> toDtoList(List<Comment> comments);
    List<Comment> toEntityList(List<CommentDto> commentDtoList);
}
