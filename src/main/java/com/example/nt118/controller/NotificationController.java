package com.example.nt118.controller;

import com.example.nt118.model.NotificationResponse;
import com.example.nt118.service.NotificationService;
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
@RequestMapping("/api/courses")
@Tag(name = "Course Notifications", description = "APIs for course notifications")
@SecurityRequirement(name = "Bearer Authentication")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(
        summary = "Get course notifications",
        description = "Get all notifications for a specific course"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved notifications",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = NotificationResponse.class))),
        @ApiResponse(responseCode = "404", description = "Course not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{courseId}/notifications")
    public ResponseEntity<NotificationResponse> getCourseNotifications(
            @PathVariable String courseId,
            @RequestHeader("Authorization") String token) {
        // TODO: Validate token and extract student ID
        String studentId = "20520123"; // This should be extracted from the token
        return ResponseEntity.ok(notificationService.getCourseNotifications(courseId, studentId));
    }
} 