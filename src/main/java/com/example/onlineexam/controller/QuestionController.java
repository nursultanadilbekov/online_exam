package com.example.onlineexam.controller;

import com.example.onlineexam.dto.QuestionRequest;
import com.example.onlineexam.entity.Question;
import com.example.onlineexam.service.interfaces.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams/{examId}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> addQuestion(@PathVariable Long examId,
                                                @RequestBody @Valid QuestionRequest questionRequest) {
        Question question = questionService.addQuestionToExam(examId, questionRequest);
        return ResponseEntity.ok(question);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getQuestions(@PathVariable Long examId) {
        return ResponseEntity.ok(questionService.getQuestionsByExamId(examId));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId,
                                                   @RequestBody @Valid QuestionRequest questionRequest) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, questionRequest));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
