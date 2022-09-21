package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.FriendRequest;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.models.UserRelationship;
import kz.bitlab.bootcamp.finalproject.repositories.UserRelationshipRepository;
import kz.bitlab.bootcamp.finalproject.services.UserRelationshipService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRelationshipServiceImpl implements UserRelationshipService {

    private final UserRelationshipRepository userRelationshipRepository;
    private final UserService userService;

    @Override
    public List<User> getFriendsOfUser(User user) {

        List<UserRelationship> relationships = userRelationshipRepository.findAllByFirstUserOrSecondUser(user, user);
        List<User> friends = new ArrayList<>();
        for (UserRelationship r : relationships) {
            if (r.getFirstUser().getId()!=user.getId() && r.getType().equals("friend")){
                friends.add(r.getFirstUser());
            }
            if (r.getSecondUser().getId()!= user.getId() && r.getType().equals("friend")){
                friends.add(r.getSecondUser());
            }
        }
        Collections.sort(friends);
        return friends;
    }

    @Override
    public List<User> checkFriends(User user, List<User> users) {
        List<User> checkedFriends = new ArrayList<>();
        List<User> myFriends = getFriendsOfUser(user);
        for (User u : users) {
            if (myFriends.contains(u)){
                checkedFriends.add(u);
            }
        }
        return checkedFriends;
    }

    @Override
    public List<User> getFriendsOfUserByKeyword(User user, String keyword) {
        List<User> friends = getFriendsOfUser(user);
        List<User> searchedUsers = userService.getUsersByKeyword(keyword);
        List<User> searchedFriends = new ArrayList<>();
        for (User u: searchedUsers){
            if (friends.contains(u)){
                searchedFriends.add(u);
            }
        }
        return searchedFriends;
    }

    @Override
    public List<User> getOnlineFriendsOfUser(User user) {
        List<UserRelationship> relationships = userRelationshipRepository.findAllByFirstUserOrSecondUser(user, user);
        List<User> onlineFriends = new ArrayList<>();
        for (UserRelationship r : relationships) {
            if (r.getFirstUser().getId()!=user.getId() && r.getType().equals("friend") && r.getFirstUser().isOnline()){
                onlineFriends.add(r.getFirstUser());
            }
            if (r.getSecondUser().getId()!= user.getId() && r.getType().equals("friend")&& r.getSecondUser().isOnline()){
                onlineFriends.add(r.getSecondUser());
            }
        }
        Collections.sort(onlineFriends);
        return onlineFriends;
    }

    @Override
    public int getNumOfFriends(User user) {
        List<UserRelationship> relationships = userRelationshipRepository.findAllByFirstUserOrSecondUser(user, user);
        int numOfFriends = 0;
        for (UserRelationship r : relationships) {
            if (r.getFirstUser().getId()!=user.getId() && r.getType().equals("friend")){
                numOfFriends++;
            }
            if (r.getSecondUser().getId()!= user.getId() && r.getType().equals("friend")){
                numOfFriends++;
            }
        }
        return numOfFriends;
    }

    @Override
    public UserRelationship addFriend(FriendRequest request) {
        UserRelationship relationship = new UserRelationship();
        relationship.setFirstUser(request.getSender());
        relationship.setSecondUser(request.getReceiver());
        relationship.setType("friend");
        return userRelationshipRepository.save(relationship);
    }

    @Override
    public void deleteFriend(Long friendId) {
        System.out.println(friendId+" asd");
        User currentUser = userService.getCurrentUser();
        User friend = userService.getUser(friendId);
        System.out.println(friend.getFullName()+" qwe");
        List<UserRelationship> relationships = userRelationshipRepository.findAllByFirstUserOrSecondUser(currentUser, currentUser);
        for (UserRelationship r : relationships) {
            if (r.getFirstUser()==friend && r.getType().equals("friend")){
                userRelationshipRepository.delete(r);
            }
            if (r.getSecondUser()==friend && r.getType().equals("friend")){
                userRelationshipRepository.delete(r);
            }
        }
    }
}
