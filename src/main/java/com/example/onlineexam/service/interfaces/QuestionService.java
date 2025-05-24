package com.example.onlineexam.service.interfaces;

import com.example.onlineexam.dto.QuestionRequest;
import com.example.onlineexam.entity.Question;

import java.util.List;

public interface QuestionService {
    Question addQuestionToExam(Long examId, QuestionRequest questionRequest);
    List<Question> getQuestionsByExamId(Long examId);
    Question getQuestionById(Long questionId);
    Question updateQuestion(Long questionId, QuestionRequest questionRequest);
    void deleteQuestion(Long questionId);
}
