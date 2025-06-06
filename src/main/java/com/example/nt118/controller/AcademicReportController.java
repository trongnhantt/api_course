package com.example.nt118.controller;

import com.example.nt118.model.AcademicReportResponse;
import com.example.nt118.service.AcademicReportService;
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

@RestController
@RequestMapping("/api/academic-report")
@Tag(name = "Academic Report", description = "APIs for student academic report")
public class AcademicReportController {

    @Autowired
    private AcademicReportService academicReportService;

    @Operation(
        summary = "Get student academic report",
        description = "Retrieves academic report for a student in a specific semester"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved academic report",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = AcademicReportResponse.class))),
        @ApiResponse(responseCode = "404", description = "Student or semester not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{studentId}/semester/{semesterId}")
    public ResponseEntity<AcademicReportResponse> getAcademicReport(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId,
            @Parameter(description = "ID of the semester", required = true)
            @PathVariable String semesterId) {
        
        AcademicReportResponse response = academicReportService.getAcademicReport(semesterId, studentId);
        return ResponseEntity.ok(response);
    }
} 