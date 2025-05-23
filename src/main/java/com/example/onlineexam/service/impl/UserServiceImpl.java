package com.example.onlineexam.service.impl;

import com.example.onlineexam.dto.TeacherRegisterRequest;
import com.example.onlineexam.entity.Role;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.UserRepository;
import com.example.onlineexam.service.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerTeacher(TeacherRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User teacher = new User();
        teacher.setEmail(request.getEmail());
        teacher.setPassword(passwordEncoder.encode(request.getPassword()));
        teacher.setRole(Role.TEACHER);

        userRepository.save(teacher);
    }
}
