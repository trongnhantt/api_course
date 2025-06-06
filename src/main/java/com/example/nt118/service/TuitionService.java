package com.example.nt118.service;

import com.example.nt118.model.TuitionResponse;

public interface TuitionService {
    TuitionResponse getStudentTuition(String studentId, String semester);
} 