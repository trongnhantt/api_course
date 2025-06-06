package com.example.nt118.service;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.model.DeadlineResponse;
import com.example.nt118.model.StudentDeadlineResponse;
import com.example.nt118.model.DeadlineListResponse;

public interface DeadlineService {
    DeadlineResponse getAllDeadlines(String courseId, String status, String type, String priority, int page, int size);
    DeadlineResponse getDeadlineById(Long id);
    DeadlineResponse createDeadline(AssignmentDeadline deadline);
    DeadlineResponse updateDeadline(Long id, AssignmentDeadline deadline);
    void deleteDeadline(Long id);
    DeadlineResponse.DeadlineSummary getDeadlineSummary();
    DeadlineResponse.DeadlineFilters getDeadlineFilters();
    StudentDeadlineResponse getStudentDeadlines(String studentId);
    DeadlineResponse getCourseDeadlines(String courseId, String status);
    DeadlineListResponse getStudentCourseDeadlines(String studentId, String courseId);
} 