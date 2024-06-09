package apap.tutorial.bacabaca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tutorial.bacabaca.model.Role;

@Repository
public interface RoleDb extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Optional<Role> findByRole(String role);
}
