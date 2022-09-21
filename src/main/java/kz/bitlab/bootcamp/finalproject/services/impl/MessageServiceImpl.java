package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Message;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.MessageRepository;
import kz.bitlab.bootcamp.finalproject.services.MessageService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Message> getMessagesForChat(Long senderId, Long receiverId) {
        List<Message> messages = new ArrayList<>();
        messages.addAll(messageRepository.findAllBySenderIdAndReceiverIdOrderByCreatedAtAsc(senderId, receiverId));
        messages.addAll(messageRepository.findAllBySenderIdAndReceiverIdOrderByCreatedAtAsc(receiverId, senderId));
        Collections.sort(messages);
        return messages;
    }

    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Long> getChatersId(Long userId) {
        List<Message> messagesList = messageRepository.findAllBySenderIdOrReceiverId(userId, userId);
        List<Long> chatersId = new ArrayList<>();
        for (Message m : messagesList) {
            if (m.getSender().getId()!=userService.getCurrentUser().getId()){
                if (!chatersId.contains(m.getSender().getId())){
                    chatersId.add(m.getSender().getId());
                }
            }
            if (m.getReceiver().getId()!=userService.getCurrentUser().getId()){
                if (!chatersId.contains(m.getReceiver().getId())){
                    chatersId.add(m.getReceiver().getId());
                }
            }
        }
        return chatersId;
    }

    @Override
    public List<Message> getMessagesOfUser(User user) {
        List<Message> messagesList = messageRepository.findAllBySenderOrReceiver(user, user);
        List<User> chaters = new ArrayList<>();
        for (Message m : messagesList) {
            if (m.getSender().getId()!=userService.getCurrentUser().getId()){
                if (!chaters.contains(m.getSender())){
                    chaters.add(m.getSender());
                }
            }
            if (m.getReceiver().getId()!=userService.getCurrentUser().getId()){
                if (!chaters.contains(m.getReceiver())){
                    chaters.add(m.getReceiver());
                }
            }
        }
        List<Message> lastMessages = new ArrayList<>();
        for (User chater : chaters){
            List<Message> messages = new ArrayList<>();
            messages.addAll(messageRepository.findAllBySenderIdAndReceiverIdOrderByCreatedAtAsc(userService.getCurrentUser().getId(), chater.getId()));
            messages.addAll(messageRepository.findAllBySenderIdAndReceiverIdOrderByCreatedAtAsc(chater.getId(), userService.getCurrentUser().getId()));
            Collections.sort(messages);
            lastMessages.add(messages.get(messages.size()-1));
        }
        return lastMessages;
    }
}
