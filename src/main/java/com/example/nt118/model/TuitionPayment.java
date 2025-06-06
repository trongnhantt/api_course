package com.example.nt118.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tuition_payments")
public class TuitionPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.STRING)
    private TuitionResponse.PaymentStatus status;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private TuitionResponse.PaymentMethod paymentMethod;
} 