package kz.bitlab.bootcamp.finalproject.repositories;

import kz.bitlab.bootcamp.finalproject.models.Comment;
import kz.bitlab.bootcamp.finalproject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPostInOrderByCreatedAtDesc(List<Post> posts);
    void deleteAllByPostId(Long postId);
}
