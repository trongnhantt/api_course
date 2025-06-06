package com.example.nt118.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeadlineListResponse {
    private String status;
    private List<DeadlineItem> data;

    @Data
    public static class DeadlineItem {
        private String id;
        private String title;
        private String description;
        private LocalDateTime dueDate;
        private String courseId;
        private String courseName;
        private String type;
        private String status;
        private LocalDateTime submittedAt;
        private Double grade;
        private Double maxGrade;
        private Long submissionId;
        private List<Attachment> attachments;
        private List<Requirement> requirements;
    }

    @Data
    public static class Attachment {
        private String id;
        private String name;
        private String url;
        private String type;
    }

    @Data
    public static class Requirement {
        private String id;
        private String description;
        private boolean isRequired;
    }
} 