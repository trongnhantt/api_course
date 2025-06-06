package com.example.nt118.controller;

import com.example.nt118.model.TuitionResponse;
import com.example.nt118.service.TuitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class TuitionController {

    private static final Logger logger = LoggerFactory.getLogger(TuitionController.class);

    @Autowired
    private TuitionService tuitionService;

    @GetMapping("/tuition")
    public ResponseEntity<TuitionResponse> getStudentTuition(
            @RequestParam String semester,
            @RequestHeader("Authorization") String token) {
        logger.info("getStudentTuition called with semester: {}, token: {}", semester, token);
        // TODO: Validate token and extract student ID
        String studentId = "20520123"; // This should be extracted from the token
        logger.info("Using hardcoded studentId: {}", studentId);
        return ResponseEntity.ok(tuitionService.getStudentTuition(studentId, semester));
    }
} 