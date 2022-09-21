package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.FriendRequest;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.models.UserRelationship;

import java.util.List;

public interface UserRelationshipService {
    List<User> getFriendsOfUser(User user);
    List<User> checkFriends(User user, List<User> users);
    List<User> getFriendsOfUserByKeyword(User user, String keyword);
    List<User> getOnlineFriendsOfUser(User user);
    int getNumOfFriends(User user);
    UserRelationship addFriend(FriendRequest request);
    void deleteFriend(Long friendId);
}
