package com.example.onlineexam.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExamResult {
    private Long examId;
    private String examTitle;
    private int score;
    private int totalQuestions;
    private List<QuestionResult> questions;
}