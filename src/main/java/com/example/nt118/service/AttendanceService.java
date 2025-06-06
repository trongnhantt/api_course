package com.example.nt118.service;

import com.example.nt118.model.AttendanceResponse;

public interface AttendanceService {
    AttendanceResponse getStudentAttendance(String studentId);
    AttendanceResponse getStudentCourseAttendance(String studentId, String courseId);
} 