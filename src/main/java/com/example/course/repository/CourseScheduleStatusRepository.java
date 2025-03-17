package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.CourseScheduleStatus;

public interface CourseScheduleStatusRepository extends JpaRepository<CourseScheduleStatus, Long> {
}
