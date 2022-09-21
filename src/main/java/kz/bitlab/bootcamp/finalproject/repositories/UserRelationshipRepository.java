package kz.bitlab.bootcamp.finalproject.repositories;

import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.models.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, Long> {
    List<UserRelationship> findAllByFirstUserOrSecondUser(User firstUser, User secondUser);
}
