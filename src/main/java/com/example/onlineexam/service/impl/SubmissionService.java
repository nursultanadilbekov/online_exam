package com.example.onlineexam.service.impl;

import com.example.onlineexam.dto.AnswerDTO;
import com.example.onlineexam.dto.AnswerSubmission;
import com.example.onlineexam.dto.ExamResult;
import com.example.onlineexam.dto.QuestionResult;
import com.example.onlineexam.entity.*;
import com.example.onlineexam.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final SubmissionRepository submissionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public void submitExam(Long examId, List<AnswerSubmission> answers, String email) {
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Submission submission = Submission.builder()
                .student(student)
                .exam(exam)
                .score(0)
                .build();

        List<com.example.onlineexam.entity.AnswerSubmission> submittedAnswers = new ArrayList<>();
        int score = 0;

        for (AnswerSubmission dto : answers) {
            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            Answer selectedAnswer = answerRepository.findById(dto.getSelectedAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));

            if (selectedAnswer.isCorrect()) {
                score++;
            }

            com.example.onlineexam.entity.AnswerSubmission as = com.example.onlineexam.entity.AnswerSubmission.builder()
                    .submission(submission)
                    .question(question)
                    .selectedAnswer(selectedAnswer)
                    .build();

            submittedAnswers.add(as);
        }

        submission.setAnswerSubmissions(submittedAnswers);
        submission.setScore(score);

        submissionRepository.save(submission);
    }
    public ExamResult getExamResult(Long examId, String email) {
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Submission submission = submissionRepository.findByExamIdAndStudentId(examId, student.getId())
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        Exam exam = submission.getExam();

        ExamResult resultDTO = new ExamResult();
        resultDTO.setExamId(exam.getId());
        resultDTO.setExamTitle(exam.getTitle());
        resultDTO.setScore(submission.getScore());
        resultDTO.setTotalQuestions(exam.getQuestions().size());

        List<QuestionResult> questionResults = new ArrayList<>();

        for (Question question : exam.getQuestions()) {
            QuestionResult qr = new QuestionResult();
            qr.setQuestionId(question.getId());
            qr.setQuestionText(question.getText());

            submission.getAnswerSubmissions().stream()
                    .filter(as -> as.getQuestion().getId().equals(question.getId()))
                    .findFirst()
                    .ifPresent(as -> {
                        qr.setSelectedAnswerId(as.getSelectedAnswer().getId());
                        qr.setSelectedAnswerText(as.getSelectedAnswer().getAnswerText());
                        qr.setCorrect(as.getSelectedAnswer().isCorrect());
                    });

            List<AnswerDTO> allAnswers = question.getAnswers().stream().map(answer -> {
                AnswerDTO ad = new AnswerDTO();
                ad.setAnswerId(answer.getId());
                ad.setAnswerText(answer.getAnswerText());
                ad.setCorrect(answer.isCorrect());
                return ad;
            }).toList();

            qr.setAllAnswers(allAnswers);
            questionResults.add(qr);
        }

        resultDTO.setQuestions(questionResults);
        return resultDTO;
    }

}
