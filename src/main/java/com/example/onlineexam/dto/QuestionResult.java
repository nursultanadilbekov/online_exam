package com.example.onlineexam.dto;

import lombok.Data;
import java.util.List;
@Data
public class QuestionResult {
    private Long questionId;
    private String questionText;
    private Long selectedAnswerId;
    private String selectedAnswerText;
    private boolean correct;
    private List<AnswerDTO> allAnswers;
}