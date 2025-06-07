package com.example.nt118.service;

import com.example.nt118.model.DeadlineSubmission;
import com.example.nt118.model.SubmissionDetailResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface DeadlineSubmissionService {
    DeadlineSubmission submitAssignment(Long notificationId, String studentId, MultipartFile[] files, String comment, String submittedAt);
    DeadlineSubmission submitAssignmentWithUrls(Long notificationId, String studentId, List<String> fileUrls, String comment, String submittedAt);
    DeadlineSubmission gradeSubmission(Long submissionId, Double grade, String feedback);
    DeadlineSubmission getStudentSubmission(String studentId, Long deadlineId);
    DeadlineSubmission getAllSubmissions(Long deadlineId);
    SubmissionDetailResponse getSubmissionDetails(Long submissionId);
    List<DeadlineSubmission> getSubmissionsByDeadlineId(Long deadlineId);
} 