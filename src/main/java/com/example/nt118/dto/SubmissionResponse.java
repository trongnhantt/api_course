package com.example.nt118.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubmissionResponse {
    private String status;
    private String message;
    private SubmissionData data;

    @Data
    public static class SubmissionData {
        private String submissionId;
        private String status;
        private Float grade;
        private String feedback;
        private LocalDateTime submittedAt;
        private LocalDateTime gradedAt;
    }
} 