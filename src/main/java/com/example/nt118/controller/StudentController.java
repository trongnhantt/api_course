package com.example.nt118.controller;

import com.example.nt118.model.StudentProfileResponse;
import com.example.nt118.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student", description = "APIs for student management")
@SecurityRequirement(name = "Bearer Authentication")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(
        summary = "Get student profile",
        description = "Get profile information of a student by student ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student profile",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = StudentProfileResponse.class))),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/profile/{studentId}")
    public ResponseEntity<StudentProfileResponse> getStudentProfile(@PathVariable String studentId) {
        try {
            StudentProfileResponse response = studentService.getStudentProfile(studentId);
            if (response.isStatus()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            StudentProfileResponse errorResponse = new StudentProfileResponse();
            errorResponse.setStatus(false);
            errorResponse.setMessage("Lỗi server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
} 