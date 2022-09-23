package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.dto.LikeDto;
import kz.bitlab.bootcamp.finalproject.dto.PostDto;
import kz.bitlab.bootcamp.finalproject.models.Like;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;

import java.util.List;

public interface LikeService {
    LikeDto addDtoLike(LikeDto likeDto);
    List<PostDto> getLikedDtoPostsOfUser(User user, List<PostDto> posts);
    void deleteDtoLike(Long postId);
}
