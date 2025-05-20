package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tuitions")
public class Tuition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal total;
    private BigDecimal paid;
    private BigDecimal due;
    private LocalDate deadline;
    
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
} 