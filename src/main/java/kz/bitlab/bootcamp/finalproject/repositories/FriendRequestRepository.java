package kz.bitlab.bootcamp.finalproject.repositories;

import kz.bitlab.bootcamp.finalproject.models.FriendRequest;
import kz.bitlab.bootcamp.finalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findAllByReceiver(User user);
    List<FriendRequest> findAllBySender(User user);
    FriendRequest findBySenderAndReceiver(User sender, User receiver);

}
