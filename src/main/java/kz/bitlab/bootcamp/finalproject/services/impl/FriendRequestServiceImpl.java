package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.FriendRequest;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.FriendRequestRepository;
import kz.bitlab.bootcamp.finalproject.services.FriendRequestService;
import kz.bitlab.bootcamp.finalproject.services.UserRelationshipService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationshipService userRelationshipService;

    @Override
    public int getNumOfFollowers(User user) {
        return friendRequestRepository.findAllByReceiver(user).size();
    }

    @Override
    public List<FriendRequest> getRequestsOfUser(User user) {
        return friendRequestRepository.findAllByReceiver(user);
    }

    @Override
    public List<FriendRequest> getSendedRequestsOfUser(User user) {
        return friendRequestRepository.findAllBySender(user);
    }

    @Override
    public List<User> getFollowersOfUser(User user) {
        List<User> followers = new ArrayList<>();
        List<FriendRequest> requests = friendRequestRepository.findAllByReceiver(user);
        for (FriendRequest request : requests) {
            followers.add(request.getSender());
        }
        Collections.sort(followers);
        return followers;
    }

    @Override
    public List<User> getFollowingsOfUser(User user) {
        List<User> followings = new ArrayList<>();
        List<FriendRequest> requests = friendRequestRepository.findAllBySender(user);
        for (FriendRequest request : requests) {
            followings.add(request.getReceiver());
        }
        Collections.sort(followings);
        return followings;
    }

    @Override
    public List<User> checkFollowers(User user, List<User> users) {
        List<User> followers = new ArrayList<>();
        for (User u : users) {
            if (friendRequestRepository.findBySenderAndReceiver(u,userService.getCurrentUser())!=null){
                followers.add(u);
            }
        }
        return followers;
    }

    @Override
    public List<User> checkFollowings(User user, List<User> users) {
        List<User> followings = new ArrayList<>();
        for (User u : users) {
            if (friendRequestRepository.findBySenderAndReceiver(userService.getCurrentUser(), u)!=null){
                followings.add(u);
            }
        }
        return followings;
    }

    @Override
    public FriendRequest getRequest(User sender, User receiver) {
        return friendRequestRepository.findBySenderAndReceiver(sender, receiver);
    }

    @Override
    public FriendRequest addRequest(User receiver) {
        FriendRequest request = new FriendRequest();
        request.setSender(userService.getCurrentUser());
        request.setReceiver(receiver);
        request.setStatus("processed");
        return friendRequestRepository.save(request);
    }

    @Override
    public void deleteRequest(Long requestId) {
        friendRequestRepository.deleteById(requestId);
    }

    @Override
    public String getDescriptionOfUser(User user) {
        List<User> users = new ArrayList<>();
        users.add(user);
        String description = "unknown";
        List<User> followers = checkFollowers(userService.getCurrentUser(), users);
        List<User> sendedRequestUsers = checkFollowings(userService.getCurrentUser(), users);
        List<User> friends = userRelationshipService.getFriendsOfUser(userService.getCurrentUser());
        if (followers.contains(user)) description = "follower";
        else if (sendedRequestUsers.contains(user)) description = "following";
        else if (friends.contains(user)) description = "friend";
        return description;
    }
}
