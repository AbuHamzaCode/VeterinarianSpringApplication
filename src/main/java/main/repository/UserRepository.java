package main.repository;

import main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *   findByUsername - method for use in UserDetailsService where check username
 *
 *   existsByUsername and existsByEmail -
 *    - for check exist or not because username and email is Unique Constraint
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
