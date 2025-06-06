package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@Table(name = "course_grades")
public class CourseGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "midterm")
    private Double midterm;

    @Column(name = "practice")
    private Double practice;

    @Column(name = "final_grade")
    private Double finalGrade;

    @Column(name = "average")
    private Double average;

    @Column(name = "letter_grade")
    private String letterGrade;

    @Column(name = "numeric_grade")
    private Double numericGrade;

    @Column(name = "status")
    private String status;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @OneToMany(mappedBy = "courseGrade", cascade = CascadeType.ALL)
    private Set<GradeComponent> gradeComponents;
} 