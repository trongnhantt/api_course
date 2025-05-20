package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String courseId;
    private String courseName;
    private Double grade;
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
} 