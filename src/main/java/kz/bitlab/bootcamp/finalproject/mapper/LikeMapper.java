package kz.bitlab.bootcamp.finalproject.mapper;

import kz.bitlab.bootcamp.finalproject.dto.LikeDto;
import kz.bitlab.bootcamp.finalproject.models.Like;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    LikeDto toDto(Like like);
    Like toEntity(LikeDto likeDto);

    List<LikeDto> toDtoList(List<Like> likes);
    List<Like> toEntityList(List<LikeDto> likeDtoList);
}
