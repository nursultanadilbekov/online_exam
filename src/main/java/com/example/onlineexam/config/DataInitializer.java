package com.example.onlineexam.config;

import com.example.onlineexam.entity.Role;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {
        userRepository.findByEmail("admin@example.com").ifPresentOrElse(
                user -> System.out.println("Admin already exists."),
                () -> {
                    User admin = User.builder()
                            .email("admin@example.com")
                            .password(passwordEncoder.encode("admin123"))
                            .role(Role.ADMIN)
                            .build();
                    userRepository.save(admin);
                    System.out.println("Admin user created.");
                }
        );
    }
}
