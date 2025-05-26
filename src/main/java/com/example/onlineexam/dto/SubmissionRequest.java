package com.example.onlineexam.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SubmissionRequest {

    @NotNull(message = "Exam ID is required")
    private Long examId;

    @NotEmpty(message = "Submitted answers are required")
    private List<SubmittedAnswer> answers;
}
