package com.example.onlineexam.service.impl;

import com.example.onlineexam.dto.ExamRequest;
import com.example.onlineexam.entity.Exam;
import com.example.onlineexam.entity.Role;
import com.example.onlineexam.entity.User;
import com.example.onlineexam.repository.ExamRepository;
import com.example.onlineexam.repository.UserRepository;
import com.example.onlineexam.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public Exam createExam(ExamRequest request, Long teacherId) {
        User currentUser = SecurityUtil.getCurrentUser();

        if (currentUser.getRole() == Role.TEACHER) {
            if (!currentUser.getId().equals(teacherId)) {
                throw new AccessDeniedException("You can create exams only for yourself.");
            }
            teacherId = currentUser.getId();
        } else if (currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only teachers or admins can create exams.");
        }

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (teacher.getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("User is not a teacher");
        }

        Exam exam = Exam.builder()
                .title(request.getTitle())
                .teacher(teacher)
                .build();

        return examRepository.save(exam);
    }

    public Exam updateExam(Long examId, ExamRequest request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found"));

        User currentUser = SecurityUtil.getCurrentUser();
        boolean isOwner = exam.getTeacher().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You can edit only your own exams.");
        }

        exam.setTitle(request.getTitle());
        return examRepository.save(exam);
    }
}
