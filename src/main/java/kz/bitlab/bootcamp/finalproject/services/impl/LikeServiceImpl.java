package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Like;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.LikeRepository;
import kz.bitlab.bootcamp.finalproject.repositories.PostRepository;
import kz.bitlab.bootcamp.finalproject.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Override
    public Like addLike(Like like) {
        if (likeRepository.findByUserIdAndPostId(like.getUser().getId(), like.getPost().getId())==null){
            Post post = postRepository.findById(like.getPost().getId()).orElseThrow();
            post.setLikesAmount(post.getLikesAmount()+1);
            postRepository.save(post);
            return likeRepository.save(like);
        }
        return null;
    }

    @Override
    public boolean deleteLike(Like like) {
        Like like1 = likeRepository.findByUserIdAndPostId(like.getUser().getId(), like.getPost().getId());
        if (like1!=null){
            Post post = postRepository.findById(like.getPost().getId()).orElseThrow();
            post.setLikesAmount(post.getLikesAmount()-1);
            postRepository.save(post);
            likeRepository.delete(like1);
            return true;
        }
        return false;
    }

    @Override
    public List<Post> getLikedPostsOfUser(User user, List<Post> posts) {
        List<Post> likedPosts = new ArrayList<>();
        for (Post post : posts) {
            if (likeRepository.findByUserAndPost(user, post)!=null){
                likedPosts.add(post);
            }
        }
        return likedPosts;
    }


}
