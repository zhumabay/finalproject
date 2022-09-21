package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getUsers();
    List<User> getUsersByKeyword(String keyword);
    User addUser(User user, String rePassword);
    User getUser(String email);
    User getUser(Long userId);
    User getCurrentUser();
    boolean updateUserPassword(String oldPassword, String newPassword, String reNewPassword);
    User updateUser(User user);
}
