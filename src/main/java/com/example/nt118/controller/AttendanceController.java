package com.example.nt118.controller;

import com.example.nt118.model.AttendanceResponse;
import com.example.nt118.model.AttendanceDetailResponse;
import com.example.nt118.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<AttendanceResponse> getStudentAttendance(@PathVariable String studentId) {
        return ResponseEntity.ok(attendanceService.getStudentAttendance(studentId));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<AttendanceResponse> getStudentCourseAttendance(
            @PathVariable String studentId,
            @PathVariable String courseId) {
        return ResponseEntity.ok(attendanceService.getStudentCourseAttendance(studentId, courseId));
    }

    @GetMapping("/course/{courseId}/student/{studentId}")
    public ResponseEntity<?> getAttendanceDetail(
            @PathVariable String courseId,
            @PathVariable String studentId) {
        try {
            AttendanceDetailResponse response = attendanceService.getAttendanceDetail(courseId, studentId);
            Map<String, Object> result = new HashMap<>();
            result.put("status", "success");
            result.put("message", "Attendance details retrieved successfully");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "error");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
} 