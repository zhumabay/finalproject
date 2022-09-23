package kz.bitlab.bootcamp.finalproject.api;

import kz.bitlab.bootcamp.finalproject.dto.LikeDto;
import kz.bitlab.bootcamp.finalproject.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping
    public LikeDto addLike(@RequestBody LikeDto likeDto){
        return likeService.addDtoLike(likeDto);
    }

    @DeleteMapping(value = "{id}")
    public void deleteLike(@PathVariable(name = "id") Long postId){
        likeService.deleteDtoLike(postId);
    }
}
