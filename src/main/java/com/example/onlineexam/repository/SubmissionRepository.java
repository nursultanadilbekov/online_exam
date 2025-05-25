package com.example.onlineexam.repository;

import com.example.onlineexam.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByExamIdAndStudentId(Long examId, Long studentId);
}
