    package com.example.onlineexam.controller;

    import com.example.onlineexam.dto.QuestionDTO;
    import com.example.onlineexam.dto.QuestionRequest;
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
        public ResponseEntity<QuestionDTO> addQuestion(@PathVariable Long examId,
                                                       @RequestBody @Valid QuestionRequest questionRequest) {
            QuestionDTO questionDTO = questionService.addQuestionToExam(examId, questionRequest);
            return ResponseEntity.ok(questionDTO);
        }

        @GetMapping
        public ResponseEntity<List<QuestionDTO>> getQuestions(@PathVariable Long examId) {
            List<QuestionDTO> dtos = questionService.getQuestionsByExamId(examId);
            return ResponseEntity.ok(dtos);
        }

        @GetMapping("/{questionId}")
        public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long questionId) {
            QuestionDTO dto = questionService.getQuestionById(questionId);
            return ResponseEntity.ok(dto);
        }

        @PutMapping("/{questionId}")
        public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long questionId,
                                                          @RequestBody @Valid QuestionRequest questionRequest) {
            QuestionDTO updated = questionService.updateQuestion(questionId, questionRequest);
            return ResponseEntity.ok(updated);
        }

        @DeleteMapping("/{questionId}")
        public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.noContent().build();
        }
    }
