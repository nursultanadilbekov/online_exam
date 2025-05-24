package com.example.onlineexam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    @NotBlank(message = "Text of the question is required")
    @Size(max = 500, message = "Question text must be less than 500 characters")
    private String text;

    @NotEmpty(message = "At least one answer is required")
    private List<AnswerRequest> answers;
}
