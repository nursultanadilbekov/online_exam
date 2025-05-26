package com.example.onlineexam.controller;

import com.example.onlineexam.dto.*;
import com.example.onlineexam.service.interfaces.SubmissionService;
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
            @RequestBody List<SubmittedAnswer> answers,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        SubmissionRequest request = new SubmissionRequest();
        request.setExamId(examId);
        request.setAnswers(answers);

        var submissionResponse = submissionService.submitExam(userDetails.getUsername(), request);
        if (submissionResponse == null) {
            return ResponseEntity.badRequest().body("Exam already submitted or error occurred");
        }
        return ResponseEntity.ok("Submission successful!");
    }

    @GetMapping("/result/{examId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ExamResult> getExamResult(
            @PathVariable Long examId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ExamResult result = submissionService.getExamResult(examId, userDetails.getUsername());
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<SubmissionResponseDTO>> getAllSubmissions(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<SubmissionResponseDTO> submissions = submissionService.getAllSubmissionsForStudent(userDetails.getUsername());
        return ResponseEntity.ok(submissions);
    }
}
