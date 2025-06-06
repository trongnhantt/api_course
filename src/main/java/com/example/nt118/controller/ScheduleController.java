package com.example.nt118.controller;

import com.example.nt118.model.ScheduleResponse;
import com.example.nt118.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/student/{studentId}/date/{date}")
    public ResponseEntity<ScheduleResponse> getStudentSchedule(
            @PathVariable String studentId,
            @PathVariable String date) {
        return ResponseEntity.ok(scheduleService.getStudentScheduleByDate(studentId, date));
    }
} 