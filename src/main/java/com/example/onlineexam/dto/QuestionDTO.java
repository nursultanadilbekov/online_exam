package com.example.onlineexam.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String text;
    private List<AnswerDTO> answers;
}
