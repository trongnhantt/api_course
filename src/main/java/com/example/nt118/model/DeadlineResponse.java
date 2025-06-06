package com.example.nt118.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class DeadlineResponse {
    private String status;
    private DeadlineData data;

    @Data
    public static class DeadlineData {
        private List<DeadlineItem> deadlines;
        private DeadlineSummary summary;
        private DeadlineFilters filters;
    }

    @Data
    public static class DeadlineItem {
        private Long id;
        private String title;
        private String description;
        private CourseInfo course;
        private LocalDateTime dueDate;
        private String status;
        private String priority;
        private String type;
        private List<AttachmentInfo> attachments;
        private SubmissionInfo submission;
        private List<ReminderInfo> reminders;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    public static class CourseInfo {
        private String courseId;
        private String courseName;
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
        private Long id;
        private String fileName;
        private String fileUrl;
        private String fileType;
        private Long fileSize;
    }

    @Data
    public static class SubmissionInfo {
        private String status;
        private LocalDateTime submitDate;
        private Double grade;
        private String feedback;
        private List<SubmittedFileInfo> submittedFiles;
    }

    @Data
    public static class SubmittedFileInfo {
        private Long id;
        private String fileName;
        private String fileUrl;
        private String fileType;
        private Long fileSize;
    }

    @Data
    public static class ReminderInfo {
        private Long id;
        private LocalDateTime reminderTime;
        private Boolean isEnabled;
    }

    @Data
    public static class DeadlineSummary {
        private Integer totalDeadlines;
        private Integer pendingDeadlines;
        private Integer completedDeadlines;
        private Integer overdueDeadlines;
        private Integer upcomingWeek;
    }

    @Data
    public static class DeadlineFilters {
        private List<CourseInfo> courses;
        private List<String> types;
        private List<String> statuses;
    }
} 