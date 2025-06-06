package com.example.nt118.repository;

import com.example.nt118.model.CourseNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseNotificationRepository extends JpaRepository<CourseNotification, Long> {
    List<CourseNotification> findByCourseIdOrderByCreatedAtDesc(String courseId);
} 