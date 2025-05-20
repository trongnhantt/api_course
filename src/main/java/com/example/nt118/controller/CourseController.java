package com.example.nt118.controller;

import com.example.nt118.model.Course;
import com.example.nt118.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Management", description = "APIs for managing student courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @Operation(
        summary = "Get all courses for a student",
        description = "Retrieves a list of all courses associated with a specific student ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved courses",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Course.class))),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Course>> getStudentCourses(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId) {
        return ResponseEntity.ok(courseService.getStudentCourses(studentId));
    }
    
    @Operation(
        summary = "Get courses by status for a student",
        description = "Retrieves a list of courses with a specific status for a student"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved courses"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<Course>> getStudentCoursesByStatus(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId,
            @Parameter(description = "Status of the courses (e.g., Active, Completed)", required = true)
            @PathVariable String status) {
        return ResponseEntity.ok(courseService.getStudentCoursesByStatus(studentId, status));
    }
    
    @Operation(
        summary = "Get courses by semester for a student",
        description = "Retrieves a list of courses for a specific semester"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved courses"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/student/{studentId}/semester/{semester}")
    public ResponseEntity<List<Course>> getStudentCoursesBySemester(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId,
            @Parameter(description = "Semester (e.g., 2024-1)", required = true)
            @PathVariable String semester) {
        return ResponseEntity.ok(courseService.getStudentCoursesBySemester(studentId, semester));
    }
    
    @Operation(
        summary = "Get course by ID",
        description = "Retrieves detailed information about a specific course"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved course"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(
            @Parameter(description = "ID of the course", required = true)
            @PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }
} 