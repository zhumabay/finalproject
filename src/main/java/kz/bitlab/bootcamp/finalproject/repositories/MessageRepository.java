package kz.bitlab.bootcamp.finalproject.repositories;

import kz.bitlab.bootcamp.finalproject.models.Message;
import kz.bitlab.bootcamp.finalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderIdAndReceiverIdOrderByCreatedAtAsc(Long senderId, Long receiverId);
    List<Message> findAllBySenderIdOrReceiverId(Long userId, Long userId2);
    List<Message> findAllBySenderOrReceiver(User user, User user1);
}
