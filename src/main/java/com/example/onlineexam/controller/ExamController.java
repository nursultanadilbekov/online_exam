package com.example.onlineexam.controller;

import com.example.onlineexam.dto.ExamCreateRequest;
import com.example.onlineexam.entity.Exam;
import com.example.onlineexam.service.impl.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public Exam createExam(@RequestBody ExamCreateRequest request) {
        return examService.createExam(request);
    }
}
