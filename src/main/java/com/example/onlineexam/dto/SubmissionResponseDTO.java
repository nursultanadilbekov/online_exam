package com.example.onlineexam.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmissionResponseDTO {
    private Long id;
    private String examTitle;
    private int score;
    private LocalDateTime submittedAt;
    private List<QuestionResult> questions;
}
