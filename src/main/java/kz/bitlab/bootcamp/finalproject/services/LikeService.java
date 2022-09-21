package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.Like;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;

import java.util.List;

public interface LikeService {
    Like addLike(Like like);
    List<Post> getLikedPostsOfUser(User user, List<Post> posts);
    boolean deleteLike(Like like);
}
