package hw2.repository;

import hw2.entity.Role;
import hw2.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRolename(RoleType rolename);
}
