package kz.bitlab.bootcamp.finalproject.api;

import kz.bitlab.bootcamp.finalproject.dto.CommentDto;
import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.services.CommentService;
import kz.bitlab.bootcamp.finalproject.services.PostService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping(value = "{userId}")
    public List<CommentDto> getProfileComments(@PathVariable(name = "userId") Long userId){
        User user = userService.getUser(userId);
        List<Post> posts = postService.getPostsOfUser(user);
        return commentService.getProfileDtoComments(posts);
    }

    @GetMapping(value = "/news")
    public List<CommentDto> getNewsComments(){
        User user = userService.getCurrentUser();
        List<Post> posts = postService.getPostsOfFriends(user);
        return commentService.getProfileDtoComments(posts);
    }

    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto commentDto){
        return commentService.addDtoComment(commentDto);
    }

    @DeleteMapping(value = "{id}")
    public void deleteComment(@PathVariable(name = "id") Long commentId){
        commentService.deleteComment(commentId);
    }

}
