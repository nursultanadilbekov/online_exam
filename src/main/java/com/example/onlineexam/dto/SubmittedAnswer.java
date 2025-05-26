package com.example.onlineexam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmittedAnswer {

    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Selected answer ID is required")
    private Long selectedAnswerId;
}
