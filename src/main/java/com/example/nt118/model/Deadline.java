package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "deadlines")
public class Deadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String courseId;
    private LocalDate dueDate;
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
} 