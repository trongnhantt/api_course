package com.example.nt118.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_notifications")
public class CourseNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private String courseId;

    private String title;
    private String content;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "is_read")
    private boolean isRead;
} 