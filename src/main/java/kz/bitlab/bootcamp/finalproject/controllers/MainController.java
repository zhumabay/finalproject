package kz.bitlab.bootcamp.finalproject.controllers;

import kz.bitlab.bootcamp.finalproject.models.*;
import kz.bitlab.bootcamp.finalproject.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final MessageService messageService;
    private final FriendRequestService friendRequestService;
    private final UserRelationshipService userRelationshipService;
    private final FileUploadService fileUploadService;
    private final ActiveUserStore activeUserStore;
    private final LikeService likeService;

    @Value("${loadURL}")
    private String loadURL;

    @GetMapping(value = "/")
    public String indexPage(Model model) {
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/usersprofile/{userId}")
    public String usersProfilePage(@PathVariable(name = "userId") Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        model.addAttribute("numOfFriends", userRelationshipService.getNumOfFriends(user));
        model.addAttribute("numOfFollowers", friendRequestService.getNumOfFollowers(user));
        model.addAttribute("description", friendRequestService.getDescriptionOfUser(user));
        List<Post> posts = postService.getPostsOfUser(user);
        model.addAttribute("posts", posts);
        model.addAttribute("comments", commentService.getCommentsOfPosts(posts));
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/news")
    public String newsPage(Model model) {
        List<Post> posts = postService.getPostsOfFriends(userService.getCurrentUser());
        model.addAttribute("posts", posts);
        model.addAttribute("comments", commentService.getCommentsOfPosts(posts));
        return "news";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/editprofile")
    public String editProfilePage() {
        return "editProfile";
    }

    @GetMapping(value = "/signin")
    public String signinPage(Model model) {
        return "signin";
    }

    @GetMapping(value = "/signup")
    public String signupPage(Model model) {
        return "signup";
    }

    @GetMapping(value = "/forbidden")
    public String forbiddenPage(Model model) {
        return "forbidden";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/search")
    public String searchPage(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("myFriends", userRelationshipService.getFriendsOfUser(userService.getCurrentUser()));
        model.addAttribute("myFollowers", friendRequestService.getFollowersOfUser(userService.getCurrentUser()));
        model.addAttribute("myFollowings", friendRequestService.getFollowingsOfUser(userService.getCurrentUser()));
        return "search";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/searchusers")
    public String searchUsers(@RequestParam(name = "keyword") String keyword,
                              Model model) {
        List<User> users = userService.getUsersByKeyword(keyword);
        model.addAttribute("users", users);
        model.addAttribute("myFriends", userRelationshipService.getFriendsOfUser(userService.getCurrentUser()));
        model.addAttribute("myFollowers", friendRequestService.getFollowersOfUser(userService.getCurrentUser()));
        model.addAttribute("myFollowings", friendRequestService.getFollowingsOfUser(userService.getCurrentUser()));
        return "search";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/friends/{userId}")
    public String friendsPage(@PathVariable(name = "userId") Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        List<User> friends = userRelationshipService.getFriendsOfUser(user);
        model.addAttribute("friends", friends);
        model.addAttribute("requests", friendRequestService.getRequestsOfUser(userService.getCurrentUser()));

        model.addAttribute("myFollowers", friendRequestService.checkFollowers(userService.getCurrentUser(), friends));
        model.addAttribute("myFollowings", friendRequestService.checkFollowings(userService.getCurrentUser(), friends));
        model.addAttribute("myFriends", userRelationshipService.checkFriends(userService.getCurrentUser(), friends));
        return "friends";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/searchfriends/{userId}")
    public String searchFriends(@PathVariable(name = "userId") Long userId,
                                @RequestParam(name = "keyword") String keyword,
                                Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        List<User> friends = userRelationshipService.getFriendsOfUserByKeyword(user, keyword);
        model.addAttribute("friends", friends);

        model.addAttribute("requests", friendRequestService.getRequestsOfUser(userService.getCurrentUser()));
        model.addAttribute("myFollowers", friendRequestService.checkFollowers(userService.getCurrentUser(), friends));
        model.addAttribute("myFollowings", friendRequestService.checkFollowings(userService.getCurrentUser(), friends));
        model.addAttribute("myFriends", userRelationshipService.checkFriends(userService.getCurrentUser(), friends));
        return "friends";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/online/{userId}")
    public String onlinePage(@PathVariable(name = "userId") Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        List<User> onlineFriends = userRelationshipService.getOnlineFriendsOfUser(user);
        model.addAttribute("onlineFriends", onlineFriends);
        model.addAttribute("myFollowers", friendRequestService.checkFollowers(userService.getCurrentUser(), onlineFriends));
        model.addAttribute("myFollowings", friendRequestService.checkFollowings(userService.getCurrentUser(), onlineFriends));
        model.addAttribute("myFriends", userRelationshipService.checkFriends(userService.getCurrentUser(), onlineFriends));
        model.addAttribute("users", activeUserStore.getUsers());
        return "online";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/followers/{userId}")
    public String followersPage(@PathVariable(name = "userId") Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        List<User> followers = friendRequestService.getFollowersOfUser(user);
        model.addAttribute("followers", followers);
        model.addAttribute("myFollowers", friendRequestService.checkFollowers(userService.getCurrentUser(), followers));
        model.addAttribute("myFollowings", friendRequestService.checkFollowings(userService.getCurrentUser(), followers));
        model.addAttribute("myFriends", userRelationshipService.checkFriends(userService.getCurrentUser(), followers));
        return "followers";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/messages")
    public String messagesPage(Model model) {
        model.addAttribute("messages", messageService.getMessagesOfUser(userService.getCurrentUser()));
        return "messages";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/chat/{receiverId}")
    public String chatPage(@PathVariable(name = "receiverId") Long receiverId, Model model) {
        model.addAttribute("receiver", userService.getUser(receiverId));
        model.addAttribute("messages", messageService.getMessagesForChat(userService.getCurrentUser().getId(), receiverId));
        return "chat";
    }

    @PostMapping(value = "/signup")
    public String signUp(@RequestParam(name = "user_email") String email,
                         @RequestParam(name = "user_password") String password,
                         @RequestParam(name = "user_re_password") String rePassword,
                         @RequestParam(name = "user_full_name") String fullName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setAvatarUrl("default");

        if (userService.addUser(user, rePassword) != null) {
            return "redirect:/signup?success";
        }
        return "redirect:/signup?error";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/updatepassword")
    public String updatePassword(
            @RequestParam(name = "user_old_password") String oldPassword,
            @RequestParam(name = "user_new_password") String newPassword,
            @RequestParam(name = "user_re_new_password") String reNewPassword) {
        if (userService.updateUserPassword(oldPassword, newPassword, reNewPassword)) {
            return "redirect:/profile?success";
        }
        return "redirect:/profile?error";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addpost")
    public String addPost(Post post, @RequestParam(name = "post_image") MultipartFile file) {
        postService.addPost(post);
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            fileUploadService.uploadImage(file, post);
        }
        return "redirect:/usersprofile/" + post.getUser().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/updatepost")
    public String updatePost(Post post, @RequestParam(name = "post_image") MultipartFile file) {
        System.out.println(post.getText());
        post.setCreatedAt(postService.getPost(post.getId()).getCreatedAt());
        postService.updatePost(post);
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            fileUploadService.uploadImage(file, post);
        }
        return "redirect:/usersprofile/" + post.getUser().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/deletepost")
    public String deletePost(@RequestParam(name = "post_id") Long postId) {
        postService.deletePost(postId);
        return "redirect:/usersprofile/" + userService.getCurrentUser().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addcomment")
    public String addComment(Comment comment, @RequestParam(name = "direction") String direction) {
        commentService.addComment(comment);
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/sendmessage")
    public String sendMessage(@RequestParam(name = "sender_id") Long senderId,
                              @RequestParam(name = "receiver_id") Long receiverId,
                              @RequestParam(name = "message") String message) {
        Message messageObj = new Message();
        messageObj.setMessage(message);
        messageObj.setSender(userService.getUser(senderId));
        messageObj.setReceiver(userService.getUser(receiverId));
        messageService.addMessage(messageObj);
        return "redirect:/chat/" + receiverId;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/acceptrequest")
    public String acceptRequest(@RequestParam(name = "sender_id") Long senderId,
                                @RequestParam(name = "direction") String direction) {
        FriendRequest request = friendRequestService.getRequest(userService.getUser(senderId), userService.getCurrentUser());
        userRelationshipService.addFriend(request);
        friendRequestService.deleteRequest(request.getId());
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/deletefollower")
    public String deleteFollower(@RequestParam(name = "sender_id") Long senderId,
                                @RequestParam(name = "direction") String direction) {
        friendRequestService.deleteFollower(senderId);
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/keepfollower")
    public String keepFollower(@RequestParam(name = "sender_id") Long senderId,
                                @RequestParam(name = "direction") String direction) {
        friendRequestService.keepFollower(senderId);
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/sendrequest")
    public String sendRequest(@RequestParam(name = "receiver_id") Long receiverId,
                              @RequestParam(name = "direction") String direction) {
        friendRequestService.addRequest(userService.getUser(receiverId));
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/undorequest")
    public String undoRequest(@RequestParam(name = "receiver_id") Long receiverId,
                              @RequestParam(name = "direction") String direction) {
        FriendRequest request = friendRequestService.getRequest(userService.getCurrentUser(), userService.getUser(receiverId));
        friendRequestService.deleteRequest(request.getId());
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/deletefriend")
    public String deleteFriend(@RequestParam(name = "friend_id") Long friendId,
                               @RequestParam(name = "direction") String direction) {
        userRelationshipService.deleteFriend(friendId);
        return "redirect:/" + direction;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/uploadava")
    public String uploadAvatar(@RequestParam(name = "user_ava") MultipartFile file) {
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
            fileUploadService.uploadAvatar(file, userService.getCurrentUser());
        }
        return "redirect:/usersprofile/" + userService.getCurrentUser().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getavatar/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getAvatar(@PathVariable(name = "token", required = false) String token) throws IOException {
        String pictureURL = loadURL + "avatars/" + token + ".jpg";
        InputStream in;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        } catch (Exception e) {
            pictureURL = loadURL + "default/avatar.jpg";
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/getimage/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable(name = "token", required = false) String token) throws IOException {
        String pictureURL = loadURL + "posts/" + token + ".jpg";
        InputStream in;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        } catch (Exception e) {
            pictureURL = loadURL + "default/image.jpg";
            ClassPathResource resource = new ClassPathResource(pictureURL);
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }
}
