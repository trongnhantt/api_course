package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "deadline_attachments")
public class DeadlineAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deadline_id")
    private AssignmentDeadline deadline;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;
} 