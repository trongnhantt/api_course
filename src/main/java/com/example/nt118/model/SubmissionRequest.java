package com.example.nt118.model;

import lombok.Data;
import java.util.List;

@Data
public class SubmissionRequest {
    private List<String> fileUrls;
    private String comment;
    private String submittedAt;
    private String notificationId;
    private String studentId;
} 