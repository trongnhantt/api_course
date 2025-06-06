package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "academic_progress")
public class AcademicProgress {
    @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "total_required_credits")
    private Integer totalRequiredCredits;

    @Column(name = "completed_credits")
    private Integer completedCredits;

    @Column(name = "remaining_credits")
    private Integer remainingCredits;

    @Column(name = "estimated_graduation")
    private String estimatedGraduation;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
} 