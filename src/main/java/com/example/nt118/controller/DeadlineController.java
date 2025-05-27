package com.example.nt118.controller;

import com.example.nt118.model.Deadline;
import com.example.nt118.service.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deadlines")
@CrossOrigin(origins = "*")
public class DeadlineController {
    @Autowired
    private DeadlineService deadlineService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Deadline>> getDeadlinesByCourseId(@PathVariable String courseId) {
        List<Deadline> deadlines = deadlineService.getDeadlinesByCourseId(courseId);
        return ResponseEntity.ok(deadlines);
    }
} 