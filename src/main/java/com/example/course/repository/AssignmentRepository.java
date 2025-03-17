package com.example.course.repository;

import com.example.course.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
