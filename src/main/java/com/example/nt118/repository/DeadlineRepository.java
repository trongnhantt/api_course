package com.example.nt118.repository;

import com.example.nt118.model.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
    List<Deadline> findByCourseId(String courseId);
} 