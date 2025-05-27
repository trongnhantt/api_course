package com.example.nt118.controller;

import com.example.nt118.dto.StudentGradeDTO;
import com.example.nt118.service.StudentGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-grades")
@CrossOrigin(origins = "*")
@Tag(name = "Student Grade Management", description = "APIs for managing student grades")
public class StudentGradeController {
    @Autowired
    private StudentGradeService studentGradeService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentGradeDTO>> getStudentGrades(@PathVariable String studentId) {
        List<StudentGradeDTO> grades = studentGradeService.getStudentGrades(studentId);
        return ResponseEntity.ok(grades);
    }
} 