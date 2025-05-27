package com.example.onlineexam.repository;

import com.example.onlineexam.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByStudentId(Long studentId);

    Optional<Submission> findTopByStudentIdAndExamIdOrderBySubmittedAtDesc(Long studentId, Long examId);

    boolean existsByStudentIdAndExamId(Long studentId, Long examId);
}
