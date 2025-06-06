package com.example.nt118.controller;

import com.example.nt118.model.*;
import com.example.nt118.service.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/deadlines")
public class DeadlineController {

    @Autowired
    private DeadlineService deadlineService;

    @GetMapping
    public ResponseEntity<?> getAllDeadlines(
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        if (studentId != null) {
            return ResponseEntity.ok(deadlineService.getStudentDeadlines(studentId));
        }
        
        return ResponseEntity.ok(deadlineService.getAllDeadlines(courseId, status, type, priority, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeadlineResponse> getDeadlineById(@PathVariable Long id) {
        return ResponseEntity.ok(deadlineService.getDeadlineById(id));
    }

    @PostMapping
    public ResponseEntity<DeadlineResponse> createDeadline(@RequestBody AssignmentDeadline deadline) {
        return ResponseEntity.ok(deadlineService.createDeadline(deadline));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeadlineResponse> updateDeadline(
            @PathVariable Long id,
            @RequestBody AssignmentDeadline deadline) {
        return ResponseEntity.ok(deadlineService.updateDeadline(id, deadline));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeadline(@PathVariable Long id) {
        deadlineService.deleteDeadline(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<DeadlineResponse.DeadlineSummary> getDeadlineSummary() {
        return ResponseEntity.ok(deadlineService.getDeadlineSummary());
    }

    @GetMapping("/filters")
    public ResponseEntity<DeadlineResponse.DeadlineFilters> getDeadlineFilters() {
        return ResponseEntity.ok(deadlineService.getDeadlineFilters());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentDeadlines(@PathVariable String studentId) {
        return ResponseEntity.ok(deadlineService.getStudentDeadlines(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<DeadlineResponse> getCourseDeadlines(
            @PathVariable String courseId,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(deadlineService.getCourseDeadlines(courseId, status));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<DeadlineListResponse> getStudentCourseDeadlines(
            @PathVariable String studentId,
            @PathVariable String courseId) {
        return ResponseEntity.ok(deadlineService.getStudentCourseDeadlines(studentId, courseId));
    }
} 