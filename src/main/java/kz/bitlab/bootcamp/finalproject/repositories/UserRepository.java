package kz.bitlab.bootcamp.finalproject.repositories;

import kz.bitlab.bootcamp.finalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAllByOrderByFullNameAsc();

    List<User> findAllByFullNameContainingIgnoreCaseOrderByFullNameAsc(String keyword);
}
