package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.dto.PostDto;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;

import java.util.List;

public interface PostService {
    List<Post> getPostsOfUser(User user);
    List<PostDto> getDtoPostsOfUser(User user);
    List<Post> getPostsOfFriends(User user);
    List<PostDto> getDtoPostsOfFriends(User user);
    Post getPost(Long postId);
    PostDto getDtoPost(Long postId);
    Post addPost(Post post);
    Post updatePost(Post post);
    void deletePost(Long postId);
}
