package com.example.onlineexam.service.interfaces;

import com.example.onlineexam.dto.QuestionDTO;
import com.example.onlineexam.dto.QuestionRequest;

import java.util.List;

public interface QuestionService {

    QuestionDTO addQuestionToExam(Long examId, QuestionRequest questionRequest);

    List<QuestionDTO> getQuestionsByExamId(Long examId);

    QuestionDTO getQuestionById(Long questionId);

    QuestionDTO updateQuestion(Long questionId, QuestionRequest questionRequest);

    void deleteQuestion(Long questionId);
}
