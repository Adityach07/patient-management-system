package cm.pm.authservice.repository;

import cm.pm.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // 1. Used during Login: Find a user by their email
    Optional<User> findByEmail(String email);

    // 2. Used during Registration: Check if an email is already registered
    boolean existsByEmail(String email);
}