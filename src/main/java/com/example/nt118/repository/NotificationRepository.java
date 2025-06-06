package com.example.nt118.repository;

import com.example.nt118.model.Notification;
import com.example.nt118.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByCourseOrderByCreatedAtDesc(Course course);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.course = :course AND :studentId NOT IN (SELECT s.studentId FROM n.readBy s)")
    long countUnreadByCourseAndStudent(@Param("course") Course course, @Param("studentId") String studentId);
} 