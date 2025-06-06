package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "grade_components")
public class GradeComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_grade_id")
    private CourseGrade courseGrade;

    @Column(name = "component_name", nullable = false)
    private String componentName;

    @Column(name = "component_type")
    private String componentType;  // quiz, assignment, project, etc.

    @Column(name = "score")
    private Double score;

    @Column(name = "max_score")
    private Double maxScore;

    @Column(name = "weight_percentage", nullable = false)
    private Double weightPercentage;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    @Column(name = "graded_date")
    private LocalDateTime gradedDate;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "graded_by")
    private String gradedBy;

    @Column(name = "is_final")
    private Boolean isFinal;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 