package com.example.nt118.repository;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.model.DeadlineSubmission;
import com.example.nt118.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeadlineSubmissionRepository extends JpaRepository<DeadlineSubmission, Long> {
    Optional<DeadlineSubmission> findByDeadlineAndStudent(AssignmentDeadline deadline, Student student);
    Optional<DeadlineSubmission> findByDeadline(AssignmentDeadline deadline);
    List<DeadlineSubmission> findByDeadlineId(Long deadlineId);
} 