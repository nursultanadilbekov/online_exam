package com.example.onlineexam.controller;

import com.example.onlineexam.dto.AnswerSubmission;
import com.example.onlineexam.dto.ExamResult;
import com.example.onlineexam.service.impl.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submit/{examId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> submitExam(
            @PathVariable Long examId,
            @RequestBody List<AnswerSubmission> answers,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        submissionService.submitExam(examId, answers, userDetails.getUsername());
        return ResponseEntity.ok("Submission successful!");
    }

    @GetMapping("/result/{examId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ExamResult> getExamResult(
            @PathVariable Long examId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ExamResult result = submissionService.getExamResult(examId, userDetails.getUsername());
        return ResponseEntity.ok(result);
    }
}
