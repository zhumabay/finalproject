package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.services.FileUploadService;
import kz.bitlab.bootcamp.finalproject.services.PostService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final UserService userService;
    private final PostService postService;

    @Value("${targetURL}")
    private String targetURL;

    @Override
    public boolean uploadAvatar(MultipartFile file, User user) {
        try{
            String fileToken = DigestUtils.sha1Hex(user.getId() + "_!");
            String fileName = fileToken +".jpg";
            byte bytes[] = file.getBytes();
//            Path path = Paths.get(targetURL+"/"+user.getEmail()+"/avatars", fileName);
            Path path = Paths.get(targetURL+"/avatars", fileName);
            Files.write(path, bytes);
            user.setAvatarUrl(fileToken);
            userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean uploadImage(MultipartFile file, Post post) {

        try{
            String fileToken = DigestUtils.sha1Hex(post.getId() + "_!");
            String fileName = fileToken +".jpg";
            byte bytes[] = file.getBytes();
            Path path = Paths.get(targetURL+"/posts", fileName);
            Files.write(path, bytes);
            post.setImageUrl(fileToken);
            postService.updatePost(post);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
