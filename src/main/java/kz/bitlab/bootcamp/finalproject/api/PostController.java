package kz.bitlab.bootcamp.finalproject.api;

import kz.bitlab.bootcamp.finalproject.dto.PostDto;
import kz.bitlab.bootcamp.finalproject.services.LikeService;
import kz.bitlab.bootcamp.finalproject.services.PostService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;

    @GetMapping(value = "/profile/{userId}")
    public List<PostDto> getPosts(@PathVariable(name = "userId") Long userId){
        return postService.getDtoPostsOfUser(userService.getUser(userId));
    }
    @GetMapping(value = "{postId}")
    public PostDto getPost(@PathVariable(name = "postId") Long postId){
        return postService.getDtoPost(postId);
    }
    @GetMapping(value = "/news")
    public List<PostDto> getDtoNews(){
        return postService.getDtoPostsOfFriends(userService.getCurrentUser());
    }
    @GetMapping(value = "/likedposts/{userId}")
    public List<PostDto> getLikedPosts(@PathVariable(name = "userId") Long userId){
        List<PostDto> posts = postService.getDtoPostsOfUser(userService.getUser(userId));
        return likeService.getLikedDtoPostsOfUser(userService.getCurrentUser(), posts);
    }
    @GetMapping(value = "/likednews")
    public List<PostDto> getLikedNews(){
        List<PostDto> posts = postService.getDtoPostsOfFriends(userService.getCurrentUser());
        return likeService.getLikedDtoPostsOfUser(userService.getCurrentUser(), posts);
    }
}
