package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String courseId;
    private String courseName;
    private Integer attended;
    private Integer total;
    private Integer absent;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
} 