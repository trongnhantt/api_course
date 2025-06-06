package com.example.nt118.controller;

import com.example.nt118.model.StudentProfileResponse;
import com.example.nt118.service.StudentProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Profile", description = "APIs for student profile management")
@SecurityRequirement(name = "Bearer Authentication")
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentProfileService;

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
        StudentProfileResponse response = studentProfileService.getStudentProfile(studentId);
        return ResponseEntity.ok(response);
    }
} 