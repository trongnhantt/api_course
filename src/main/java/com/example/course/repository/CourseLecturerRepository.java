package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.CourseLecturer;
import com.example.course.entity.composite.CourseLecturerId;

public interface CourseLecturerRepository extends JpaRepository<CourseLecturer, CourseLecturerId> {
}
