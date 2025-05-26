package com.example.onlineexam.dto;

import lombok.Data;

@Data
public class AnswerDTO {
    private Long id;
    private String answerText;
    private boolean correct;
}
