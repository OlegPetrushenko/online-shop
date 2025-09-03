package ait.cohort63.online_shop.repository;

import ait.cohort63.online_shop.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByTitle(String title);
}
