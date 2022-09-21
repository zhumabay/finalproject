package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.PostRepository;
import kz.bitlab.bootcamp.finalproject.services.CommentService;
import kz.bitlab.bootcamp.finalproject.services.PostService;
import kz.bitlab.bootcamp.finalproject.services.UserRelationshipService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private  final PostRepository postRepository;
    private final UserRelationshipService userRelationshipService;
    private final CommentService commentService;

    @Override
    public List<Post> getPostsOfUser(User user) {
        return postRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<Post> getPostsOfFriends(User user) {
        return postRepository.findAllByUserInOrderByCreatedAtDesc(userRelationshipService.getFriendsOfUser(user));
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Override
    public Post addPost(Post post) {
        post.setImageUrl("default");
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        if (postRepository.findById(postId)!=null) {
            commentService.deleteAllCommentsByPostId(postId);
            postRepository.deleteById(postId);
        }
    }
}
