package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @Column(name = "semester_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "gpa")
    private Double gpa;

    @Column(name = "total_credits")
    private Integer totalCredits;

    @Column(name = "completed_courses")
    private Integer completedCourses;

    @Column(name = "in_progress_courses")
    private Integer inProgressCourses;

    @OneToMany(mappedBy = "semester")
    private Set<CourseGrade> courseGrades;
} 