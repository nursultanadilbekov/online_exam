package com.example.onlineexam.service.impl;

import com.example.onlineexam.dto.ExamCreateRequest;
import com.example.onlineexam.entity.Exam;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ExamRepository;
import com.example.onlineexam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public Exam createExam(ExamCreateRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User teacher = userRepository.findByEmail(email).orElseThrow();

        Exam exam = Exam.builder()
                .title(request.getTitle())
                .teacher(teacher)
                .build();

        return examRepository.save(exam);
    }
}
