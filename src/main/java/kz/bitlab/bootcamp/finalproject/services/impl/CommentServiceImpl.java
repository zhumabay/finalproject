package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Comment;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.repositories.CommentRepository;
import kz.bitlab.bootcamp.finalproject.repositories.PostRepository;
import kz.bitlab.bootcamp.finalproject.services.CommentService;
import kz.bitlab.bootcamp.finalproject.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<Comment> getCommentsOfPosts(List<Post> posts) {
        return commentRepository.getAllByPostInOrderByCreatedAtDesc(posts);
    }

    @Override
    public Comment addComment(Comment comment) {
        Post post = postRepository.findById(comment.getPost().getId()).orElseThrow();
        post.setCommentsAmount(post.getCommentsAmount()+1);
        postRepository.save(post);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteAllCommentsByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        Post post = postRepository.findById(comment.getPost().getId()).orElseThrow();
        post.setCommentsAmount(post.getCommentsAmount()-1);
        postRepository.save(post);
        commentRepository.deleteById(commentId);
    }
}
