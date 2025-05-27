package com.example.nt118.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_grade")
public class StudentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "process_grade")
    private Float processGrade;

    @Column(name = "practice_grade")
    private Float practiceGrade;

    @Column(name = "midterm_grade")
    private Float midtermGrade;

    @Column(name = "final_grade")
    private Float finalGrade;

    @Column(name = "average_grade")
    private Float averageGrade;
} 