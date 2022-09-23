package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.FriendRequest;
import kz.bitlab.bootcamp.finalproject.models.User;

import java.util.List;

public interface FriendRequestService {
    int getNumOfFollowers(User user);
    List<FriendRequest> getRequestsOfUser(User user);
    List<FriendRequest> getSendedRequestsOfUser(User user);
    List<User> getFollowersOfUser(User user);
    List<User> getFollowingsOfUser(User user);
    List<User> checkFollowers(User user, List<User> users);
    List<User> checkFollowings(User user, List<User> users);
    FriendRequest getRequest(User sender, User receiver);
    FriendRequest addRequest(User receiver);
    FriendRequest keepFollower(Long senderId);
    void deleteFollower(Long senderId);
    void deleteRequest(Long requestId);

    String getDescriptionOfUser(User user);

}
