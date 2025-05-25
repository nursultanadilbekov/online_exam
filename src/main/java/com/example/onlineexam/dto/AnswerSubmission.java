package com.example.onlineexam.dto;

import lombok.Data;

@Data
public class AnswerSubmission {
    private Long questionId;
    private Long selectedAnswerId;
}
