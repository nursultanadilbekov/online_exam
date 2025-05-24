package com.example.onlineexam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AnswerRequest {
    @NotBlank(message = "Answer text is required")
    @Size(max = 255, message = "Answer text must be less than 256 characters")
    private String answerText;

    private boolean correct;
}
