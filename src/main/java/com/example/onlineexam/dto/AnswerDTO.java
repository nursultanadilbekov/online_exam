package com.example.onlineexam.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long answerId;
    private String answerText;
    private boolean correct;
}
