package com.example.onlineexam.mapper;

import com.example.onlineexam.dto.AnswerDTO;
import com.example.onlineexam.dto.QuestionResult;
import com.example.onlineexam.dto.SubmissionResponseDTO;
import com.example.onlineexam.entity.Answer;
import com.example.onlineexam.entity.Question;
import com.example.onlineexam.entity.Submission;

import java.util.List;
import java.util.stream.Collectors;

public class SubmissionMapper {

    public static SubmissionResponseDTO toDTO(Submission submission) {
        SubmissionResponseDTO dto = new SubmissionResponseDTO();
        dto.setId(submission.getId());
        dto.setExamTitle(submission.getExam().getTitle());
        dto.setScore(submission.getScore());
        dto.setSubmittedAt(submission.getSubmittedAt());

        List<QuestionResult> questions = submission.getAnswerSubmissions()
                .stream()
                .map(answerSubmission -> {
                    Question question = answerSubmission.getQuestion();
                    Answer selectedAnswer = answerSubmission.getSelectedAnswer();

                    QuestionResult qr = new QuestionResult();
                    qr.setQuestionId(question.getId());
                    qr.setQuestionText(question.getText());
                    qr.setSelectedAnswerId(selectedAnswer.getId());
                    qr.setSelectedAnswerText(selectedAnswer.getAnswerText());
                    qr.setCorrect(selectedAnswer.isCorrect());

                    List<AnswerDTO> allAnswers = question.getAnswers().stream()
                            .map(answer -> {
                                AnswerDTO answerDTO = new AnswerDTO();
                                answerDTO.setId(answer.getId());
                                answerDTO.setAnswerText(answer.getAnswerText());
                                return answerDTO;
                            })
                            .collect(Collectors.toList());

                    qr.setAllAnswers(allAnswers);

                    return qr;
                })
                .collect(Collectors.toList());

        dto.setQuestions(questions);

        return dto;
    }
}
