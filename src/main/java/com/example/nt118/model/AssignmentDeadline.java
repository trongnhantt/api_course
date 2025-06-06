package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "assignment_deadlines")
public class AssignmentDeadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "deadline_date", nullable = false)
    private LocalDateTime deadlineDate;

    @Column(name = "submission_type")
    @Enumerated(EnumType.STRING)
    private SubmissionType submissionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private DeadlinePriority priority;

    @Column(name = "max_points")
    private Double maxPoints;

    @Column(name = "weight_percentage")
    private Double weightPercentage;

    @Column(name = "is_group_work")
    private Boolean isGroupWork;

    @Column(name = "submission_url")
    private String submissionUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DeadlineStatus status;

    @OneToMany(mappedBy = "deadline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DeadlineAttachment> attachments = new HashSet<>();

    @OneToMany(mappedBy = "deadline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<DeadlineSubmission> submissions = new HashSet<>();

    @OneToMany(mappedBy = "deadline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DeadlineReminder> reminders = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum DeadlineStatus {
        PENDING,
        COMPLETED,
        OVERDUE
    }

    public enum DeadlinePriority {
        URGENT,
        HIGH,
        MEDIUM,
        LOW
    }

    public enum SubmissionType {
        ASSIGNMENT,
        EXAM,
        PROJECT,
        HOMEWORK,
        PRESENTATION
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 