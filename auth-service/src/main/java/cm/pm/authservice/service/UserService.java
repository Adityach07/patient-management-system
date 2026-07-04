package cm.pm.authservice.service;

import cm.pm.authservice.model.User;
import cm.pm.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Find user by email (used for login)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 2. Check if user exists (used for registration)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 3. Save a new user to the database
    public User save(User user) {
        return userRepository.save(user);
    }
}