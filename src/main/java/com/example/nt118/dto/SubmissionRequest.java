package com.example.nt118.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmissionRequest {
    private String notificationId;
    private String courseId;
    private String studentId;
    private List<SubmissionFileDTO> submissionFiles;
    private String submissionText;
    private LocalDateTime submittedAt;
} 