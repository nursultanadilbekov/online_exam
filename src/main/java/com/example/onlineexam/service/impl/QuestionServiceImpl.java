package com.example.onlineexam.service.impl;

import com.example.onlineexam.dto.*;
import com.example.onlineexam.entity.*;
import com.example.onlineexam.repository.*;
import com.example.onlineexam.security.SecurityUtil;
import com.example.onlineexam.service.interfaces.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public Question addQuestionToExam(Long examId, QuestionRequest questionRequest) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("Exam not found with id: " + examId));

        User currentUser = SecurityUtil.getCurrentUser();
        boolean isOwner = exam.getTeacher().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You can only add questions to your own exams.");
        }

        if (!StringUtils.hasText(questionRequest.getText())) {
            throw new IllegalArgumentException("Question text must not be empty");
        }

        if (questionRequest.getAnswers() == null || questionRequest.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("Question must have at least one answer");
        }

        boolean hasCorrectAnswer = questionRequest.getAnswers().stream().anyMatch(AnswerRequest::isCorrect);
        if (!hasCorrectAnswer) {
            throw new IllegalArgumentException("At least one answer must be marked as correct");
        }

        Question question = Question.builder()
                .text(questionRequest.getText())
                .exam(exam)
                .build();

        questionRequest.getAnswers().forEach(answerReq -> {
            if (!StringUtils.hasText(answerReq.getAnswerText())) {
                throw new IllegalArgumentException("Answer text must not be empty");
            }

            Answer answer = Answer.builder()
                    .text(answerReq.getAnswerText())
                    .correct(answerReq.isCorrect())
                    .question(question)
                    .build();

            question.getAnswers().add(answer);
        });

        return questionRepository.save(question);
    }


    @Override
    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    @Override
    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));
    }

    @Override
    public Question updateQuestion(Long questionId, QuestionRequest questionRequest) {
        Question question = getQuestionById(questionId);

        if (!StringUtils.hasText(questionRequest.getText())) {
            throw new IllegalArgumentException("Question text must not be empty");
        }

        question.setText(questionRequest.getText());

        // Удаляем старые ответы
        answerRepository.deleteAll(question.getAnswers());
        question.getAnswers().clear();

        // Добавляем новые ответы
        questionRequest.getAnswers().forEach(answerReq -> {
            Answer answer = Answer.builder()
                    .text(answerReq.getAnswerText())
                    .correct(answerReq.isCorrect())
                    .question(question)
                    .build();
            question.getAnswers().add(answer);
        });

        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        Question question = getQuestionById(questionId);
        questionRepository.delete(question);
    }
}
