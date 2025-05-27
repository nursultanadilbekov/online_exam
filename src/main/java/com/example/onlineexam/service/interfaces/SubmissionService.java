package com.example.onlineexam.service.interfaces;

import com.example.onlineexam.dto.ExamResult;
import com.example.onlineexam.dto.SubmissionRequest;
import com.example.onlineexam.dto.SubmissionResponseDTO;

import java.util.List;

public interface SubmissionService {

    SubmissionResponseDTO submitExam(String userEmail, SubmissionRequest request);
    ExamResult getExamResult(Long examId, String userEmail);
    List<SubmissionResponseDTO> getAllSubmissionsForStudent(String userEmail);
}
