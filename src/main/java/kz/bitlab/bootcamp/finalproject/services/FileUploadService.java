package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    boolean uploadAvatar(MultipartFile file, User user);
    boolean uploadImage(MultipartFile file, Post post);
}
