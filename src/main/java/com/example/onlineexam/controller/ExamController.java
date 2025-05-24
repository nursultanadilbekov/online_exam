package com.example.onlineexam.controller;

import com.example.onlineexam.dto.ExamRequest;
import com.example.onlineexam.entity.Exam;
import com.example.onlineexam.service.impl.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody @Valid ExamRequest request,
                                           @AuthenticationPrincipal(expression = "id") Long teacherId) {
        Exam exam = examService.createExam(request, teacherId);
        return ResponseEntity.ok(exam);
    }
}
