package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String courseId;
    private String courseName;
    private Integer credits;
    private String semester;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Active, Completed, etc.
    private String instructor;
    private String room;
    private String schedule; // e.g. "Monday, Wednesday 8:00-9:30"
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
} 