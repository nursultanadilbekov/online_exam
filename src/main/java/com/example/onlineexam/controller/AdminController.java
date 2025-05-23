package com.example.onlineexam.controller;

import com.example.onlineexam.dto.TeacherRegisterRequest;
import com.example.onlineexam.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-teacher")
    public ResponseEntity<String> registerTeacher(@RequestBody TeacherRegisterRequest request) {
        userService.registerTeacher(request);
        return ResponseEntity.ok("Teacher registered successfully");
    }
}
