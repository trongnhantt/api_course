package com.example.nt118.service;

import com.example.nt118.model.Attendance;
import com.example.nt118.model.AttendanceResponse;
import com.example.nt118.model.AttendanceDetailResponse;
import java.util.List;

public interface AttendanceService {
    AttendanceResponse getStudentAttendance(String studentId);
    AttendanceResponse getStudentCourseAttendance(String studentId, String courseId);
    List<Attendance> getAttendanceByStudentId(String studentId);
    List<Attendance> getAttendanceByCourseId(String courseId);
    AttendanceDetailResponse getAttendanceDetail(String courseId, String studentId);
} 