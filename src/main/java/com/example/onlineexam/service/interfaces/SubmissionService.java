package com.example.onlineexam.service.interfaces;

import com.example.onlineexam.dto.ExamResult;
import com.example.onlineexam.dto.SubmissionRequest;
import com.example.onlineexam.dto.SubmissionResponseDTO;

import java.util.List;

public interface SubmissionService {

    /**
     * Обрабатывает сдачу экзамена студентом
     * @param userEmail email студента
     * @param request DTO с данными сдачи экзамена (ID экзамена и ответы)
     * @return DTO с результатами сохранённой сдачи, или null если не найден студент/экзамен или уже сдан экзамен
     */
    SubmissionResponseDTO submitExam(String userEmail, SubmissionRequest request);

    /**
     * Получить результат экзамена для студента
     * @param examId ID экзамена
     * @param userEmail email студента
     * @return DTO с результатами экзамена, или null если результат не найден
     */
    ExamResult getExamResult(Long examId, String userEmail);

    /**
     * Получить список всех сдач экзаменов для студента
     * @param userEmail email студента
     * @return список DTO с результатами сдач
     */
    List<SubmissionResponseDTO> getAllSubmissionsForStudent(String userEmail);
}
