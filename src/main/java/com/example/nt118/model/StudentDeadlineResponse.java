package com.example.nt118.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class StudentDeadlineResponse {
    private String status;
    private String message;
    private int totalDeadlines;
    private int pendingDeadlines;
    private int completedDeadlines;
    private int overdueDeadlines;
    private List<Map<String, Object>> deadlines;

    @Data
    public static class DeadlineData {
        private List<DeadlineItem> deadlines;
        private DeadlineSummary summary;
    }

    @Data
    public static class DeadlineItem {
        private String id;
        private SubjectInfo subject;
        private String name;
        private String description;
        private LocalDateTime deadline;
        private String status;
        private String priority;
        private List<AttachmentInfo> attachments;
    }

    @Data
    public static class SubjectInfo {
        private String code;
        private String name;
        private LecturerInfo lecturer;
    }

    @Data
    public static class LecturerInfo {
        private String id;
        private String name;
        private String email;
    }

    @Data
    public static class AttachmentInfo {
        private String fileName;
        private String fileUrl;
    }

    @Data
    public static class DeadlineSummary {
        private Integer totalDeadlines;
        private Integer pendingDeadlines;
        private Integer completedDeadlines;
        private Integer overdueDeadlines;
    }
} 