package kz.bitlab.bootcamp.finalproject.repositories;

import kz.bitlab.bootcamp.finalproject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
