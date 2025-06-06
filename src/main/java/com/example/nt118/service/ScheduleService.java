package com.example.nt118.service;

import com.example.nt118.model.ScheduleResponse;

public interface ScheduleService {
    ScheduleResponse getStudentScheduleByDate(String studentId, String date);
} 