package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.dto.PostDto;
import kz.bitlab.bootcamp.finalproject.mapper.PostMapper;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.PostRepository;
import kz.bitlab.bootcamp.finalproject.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRelationshipService userRelationshipService;
    private final FriendRequestService friendRequestService;
    private final CommentService commentService;
    private final PostMapper postMapper;

    @Override
    public List<Post> getPostsOfUser(User user) {
        return postRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Override
    public List<PostDto> getDtoPostsOfUser(User user) {
        return postMapper.toDtoList(postRepository.findAllByUserOrderByCreatedAtDesc(user));
    }

    @Override
    public List<PostDto> getDtoPostsOfFriends(User user) {
        List<User> followings = friendRequestService.getFollowingsOfUser(user);
        List<User> friends = userRelationshipService.getFriendsOfUser(user);
        List<User> userList = Stream.concat(followings.stream(),friends.stream()).toList();
        return postMapper.toDtoList(postRepository.findAllByUserInOrderByCreatedAtDesc(userList));
    }

    @Override
    public List<Post> getPostsOfFriends(User user) {
        List<User> followings = friendRequestService.getFollowingsOfUser(user);
        List<User> friends = userRelationshipService.getFriendsOfUser(user);
        List<User> userList = Stream.concat(followings.stream(),friends.stream()).toList();
        return postRepository.findAllByUserInOrderByCreatedAtDesc(userList);
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Override
    public PostDto getDtoPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return postMapper.toDto(post);
    }

    @Override
    public Post addPost(Post post) {
        post.setImageUrl("default");
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        postRepository.findById(post.getId()).orElseThrow();
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
