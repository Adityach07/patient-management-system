package cm.pm.authservice.service;

import cm.pm.authservice.dto.AuthResponse;
import cm.pm.authservice.dto.LoginRequest;
import cm.pm.authservice.dto.RegisterRequest;
import cm.pm.authservice.model.User;
import cm.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;




    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 1. Register a new user
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userService.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Set default role if not provided
        String role = (request.getRole() == null || request.getRole().isEmpty()) ? "ROLE_USER" : request.getRole();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        // Build and save user (hashing the password)
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // HASH PASSWORD
                .role(role)
                .build();

        userService.save(user);

        // Generate JWT Token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    // 2. Authenticate/Login user
    public Optional<AuthResponse> authenticate(LoginRequest request) {
        return userService.findByEmail(request.getEmail())
                // Verify password matches the stored BCrypt hash
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                // If verified, generate token and wrap in AuthResponse
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
                    return AuthResponse.builder()
                            .token(token)
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build();
                });
    }

    // 3. Validate JWT Token
    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}