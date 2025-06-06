package com.example.nt118.controller;

import com.example.nt118.model.AttendanceResponse;
import com.example.nt118.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
} 