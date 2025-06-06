package com.example.nt118.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmissionDetailResponse {
    private String status;
    private String message;
    private SubmissionDetailData data;

    @Data
    public static class SubmissionDetailData {
        private String submissionId;
        private String notificationId;
        private String courseId;
        private String studentId;
        private List<SubmissionFileDTO> submissionFiles;
        private String submissionText;
        private String status;
        private Float grade;
        private String feedback;
        private LocalDateTime submittedAt;
        private LocalDateTime gradedAt;
    }
} 