package com.example.nt118.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubmissionDetailResponse {
    private String status;
    private SubmissionData data;

    @Data
    public static class SubmissionData {
        private Long id;
        private StudentInfo student;
        private LocalDateTime submitDate;
        private Double grade;
        private String feedback;
        private String status;
        private List<SubmittedFile> submittedFiles;
    }

    @Data
    public static class StudentInfo {
        private String studentId;
        private String name;
        private String email;
        private String className;
    }

    @Data
    public static class SubmittedFile {
        private Long id;
        private String fileName;
        private String fileUrl;
        private String fileType;
        private Long fileSize;
    }
} 