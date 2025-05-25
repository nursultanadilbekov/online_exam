    package com.example.onlineexam.controller;

    import com.example.onlineexam.dto.QuestionDTO;
    import com.example.onlineexam.dto.QuestionRequest;
    import com.example.onlineexam.entity.Question;
    import com.example.onlineexam.mapper.QuestionMapper;
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
        public ResponseEntity<List<QuestionDTO>> getQuestions(@PathVariable Long examId) {
            List<QuestionDTO> dtos = questionService.getQuestionsByExamId(examId)
                    .stream()
                    .map(QuestionMapper::toDTO)
                    .toList();
            return ResponseEntity.ok(dtos);
        }

        @GetMapping("/{questionId}")
        public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long questionId) {
            QuestionDTO dto = QuestionMapper.toDTO(questionService.getQuestionById(questionId));
            return ResponseEntity.ok(dto);
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
