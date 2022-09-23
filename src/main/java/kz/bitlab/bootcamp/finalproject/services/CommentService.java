package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.dto.CommentDto;
import kz.bitlab.bootcamp.finalproject.models.Comment;
import kz.bitlab.bootcamp.finalproject.models.Post;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsOfPosts(List<Post> posts);
    List<CommentDto> getProfileDtoComments(List<Post> posts);
    Comment addComment(Comment comment);
    CommentDto addDtoComment(CommentDto commentDto);
    void deleteAllCommentsByPostId(Long postId);
    void deleteComment(Long commentId);
}
