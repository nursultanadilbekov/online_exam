package com.example.onlineexam.mapper;

import com.example.onlineexam.dto.AnswerDTO;
import com.example.onlineexam.entity.Answer;

public class AnswerMapper {

    public static AnswerDTO toDTO(Answer answer) {
        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        dto.setCorrect(answer.isCorrect());
        return dto;
    }

    public static Answer toEntity(AnswerDTO dto) {
        Answer answer = new Answer();
        answer.setId(dto.getId());
        answer.setText(dto.getText());
        answer.setCorrect(dto.isCorrect());
        return answer;
    }
}
