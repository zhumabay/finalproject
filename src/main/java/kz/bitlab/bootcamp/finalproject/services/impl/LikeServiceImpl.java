package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.dto.LikeDto;
import kz.bitlab.bootcamp.finalproject.dto.PostDto;
import kz.bitlab.bootcamp.finalproject.mapper.LikeMapper;
import kz.bitlab.bootcamp.finalproject.mapper.PostMapper;
import kz.bitlab.bootcamp.finalproject.models.Like;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.LikeRepository;
import kz.bitlab.bootcamp.finalproject.repositories.PostRepository;
import kz.bitlab.bootcamp.finalproject.services.LikeService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final LikeMapper likeMapper;
    private final UserService userService;

    @Override
    public LikeDto addDtoLike(LikeDto likeDto) {
        Like like = likeMapper.toEntity(likeDto);
        like.setUser(userService.getCurrentUser());
        if (likeRepository.findByUserIdAndPostId(like.getUser().getId(), like.getPost().getId())==null){
            Post post = postRepository.findById(like.getPost().getId()).orElseThrow();
            post.setLikesAmount(post.getLikesAmount()+1);
            postRepository.save(post);
            return likeMapper.toDto(likeRepository.save(like));
        }
        return null;
    }

    @Override
    public void deleteDtoLike(Long likeId) {
        Like like = likeRepository.findByUserIdAndPostId(userService.getCurrentUser().getId(), likeId);
        if (like!=null){
            Post post = postRepository.findById(like.getPost().getId()).orElseThrow();
            post.setLikesAmount(post.getLikesAmount()-1);
            postRepository.save(post);
            likeRepository.delete(like);
        }
    }

    @Override
    public List<PostDto> getLikedDtoPostsOfUser(User user, List<PostDto> posts) {
        List<Post> likedPosts = new ArrayList<>();
        for (Post post : postMapper.toEntityList(posts)) {
            if (likeRepository.findByUserAndPost(user, post)!=null){
                likedPosts.add(post);
            }
        }
        return postMapper.toDtoList(likedPosts);
    }
}
