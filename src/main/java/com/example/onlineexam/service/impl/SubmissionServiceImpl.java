package com.example.onlineexam.service.impl;

import com.example.onlineexam.dto.*;
import com.example.onlineexam.entity.*;
import com.example.onlineexam.entity.AnswerSubmission;
import com.example.onlineexam.repository.*;
import com.example.onlineexam.service.interfaces.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    @Transactional
    public SubmissionResponseDTO submitExam(String userEmail, SubmissionRequest request) {
        Optional<User> optionalStudent = userRepository.findByEmail(userEmail);
        if (optionalStudent.isEmpty()) return null;
        User student = optionalStudent.get();

        Optional<Exam> optionalExam = examRepository.findById(request.getExamId());
        if (optionalExam.isEmpty()) return null;
        Exam exam = optionalExam.get();

        boolean alreadySubmitted = submissionRepository.existsByStudentIdAndExamId(student.getId(), request.getExamId());
        if (alreadySubmitted) return null;

        Submission submission = Submission.builder()
                .student(student)
                .exam(exam)
                .score(0)
                .submittedAt(LocalDateTime.now())
                .build();

        submission.setAnswerSubmissions(new ArrayList<>());

        int score = 0;

        for (SubmittedAnswer sa : request.getAnswers()) {
            Optional<Question> optionalQuestion = questionRepository.findById(sa.getQuestionId());
            if (optionalQuestion.isEmpty()) continue;
            Question question = optionalQuestion.get();

            Optional<Answer> optionalAnswer = answerRepository.findById(sa.getSelectedAnswerId());
            if (optionalAnswer.isEmpty()) continue;
            Answer selectedAnswer = optionalAnswer.get();

            if (selectedAnswer.isCorrect()) score++;

            AnswerSubmission answerSubmission = AnswerSubmission.builder()
                    .submission(submission)
                    .question(question)
                    .selectedAnswer(selectedAnswer)
                    .build();

            submission.getAnswerSubmissions().add(answerSubmission);
        }

        submission.setScore(score);

        Submission savedSubmission = submissionRepository.save(submission);

        return mapToSubmissionResponse(savedSubmission);
    }

    @Override
    public ExamResult getExamResult(Long examId, String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) return null;
        User student = optionalUser.get();

        Optional<Submission> optionalSubmission = submissionRepository
                .findTopByStudentIdAndExamIdOrderBySubmittedAtDesc(student.getId(), examId);
        if (optionalSubmission.isEmpty()) return null;

        Submission submission = optionalSubmission.get();
        return mapToExamResult(submission);
    }

    @Override
    public List<SubmissionResponseDTO> getAllSubmissionsForStudent(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) return Collections.emptyList();
        User student = optionalUser.get();

        List<Submission> submissions = submissionRepository.findByStudentId(student.getId());
        List<SubmissionResponseDTO> dtos = new ArrayList<>();
        for (Submission s : submissions) {
            dtos.add(mapToSubmissionResponse(s));
        }
        return dtos;
    }

    private ExamResult mapToExamResult(Submission submission) {
        ExamResult result = new ExamResult();
        result.setExamId(submission.getExam().getId());
        result.setExamTitle(submission.getExam().getTitle());
        result.setScore(submission.getScore());
        result.setTotalQuestions(submission.getExam().getQuestions().size());

        List<QuestionResult> questionResults = new ArrayList<>();
        for (AnswerSubmission as : submission.getAnswerSubmissions()) {
            Question question = as.getQuestion();
            Answer selectedAnswer = as.getSelectedAnswer();

            QuestionResult qr = new QuestionResult();
            qr.setQuestionId(question.getId());
            qr.setQuestionText(question.getText());
            qr.setSelectedAnswerId(selectedAnswer.getId());
            qr.setSelectedAnswerText(selectedAnswer.getAnswerText());
            qr.setCorrect(selectedAnswer.isCorrect());

            List<AnswerDTO> allAnswers = new ArrayList<>();
            for (Answer ans : question.getAnswers()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setId(ans.getId());
                answerDTO.setAnswerText(ans.getAnswerText());
                answerDTO.setCorrect(ans.isCorrect());
                allAnswers.add(answerDTO);
            }

            qr.setAllAnswers(allAnswers);
            questionResults.add(qr);
        }

        result.setQuestions(questionResults);
        return result;
    }

    private SubmissionResponseDTO mapToSubmissionResponse(Submission s) {
        SubmissionResponseDTO dto = new SubmissionResponseDTO();
        dto.setId(s.getId());
        dto.setExamTitle(s.getExam().getTitle());
        dto.setScore(s.getScore());
        dto.setSubmittedAt(s.getSubmittedAt());
        return dto;
    }
}
