package com.example.nt118.repository;

import com.example.nt118.model.AcademicProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AcademicProgressRepository extends JpaRepository<AcademicProgress, Long> {
    Optional<AcademicProgress> findByStudentStudentId(String studentId);
} 