package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.Message;
import kz.bitlab.bootcamp.finalproject.models.User;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesForChat(Long senderId, Long receiverId);
    Message addMessage(Message message);
    List<Long> getChatersId(Long userId);
    List<Message> getMessagesOfUser(User user);
}
