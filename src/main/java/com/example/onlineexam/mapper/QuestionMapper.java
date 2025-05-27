package com.example.onlineexam.mapper;

import com.example.onlineexam.dto.QuestionDTO;
import com.example.onlineexam.dto.AnswerDTO;
import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Answer;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionDTO toDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setText(question.getText());
        List<AnswerDTO> answers = question.getAnswers()
                .stream()
                .map(AnswerMapper::toDTO)
                .collect(Collectors.toList());
        dto.setAnswers(answers);
        return dto;
    }

    public static Question toEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setId(dto.getId());
        question.setText(dto.getText());
        List<Answer> answers = dto.getAnswers()
                .stream()
                .map(AnswerMapper::toEntity)
                .collect(Collectors.toList());
        question.setAnswers(answers);
        return question;
    }
}
