package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "session_date")
    private LocalDate sessionDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Column(name = "note")
    private String note;

    public enum AttendanceStatus {
        PRESENT, ABSENT, LATE
    }
} 