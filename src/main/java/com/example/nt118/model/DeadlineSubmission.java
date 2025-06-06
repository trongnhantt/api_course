package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@Entity
@Table(name = "deadline_submissions")
public class DeadlineSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deadline_id")
    @JsonBackReference
    private AssignmentDeadline deadline;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "submit_date")
    private LocalDateTime submitDate;

    @Column(name = "grade")
    private Double grade;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<SubmissionFile> submittedFiles;

    public enum SubmissionStatus {
        NOT_SUBMITTED, SUBMITTED, GRADED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeadlineSubmission that = (DeadlineSubmission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 