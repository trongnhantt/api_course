package com.example.nt118.controller;

import com.example.nt118.model.*;
import com.example.nt118.service.DeadlineSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/submissions")
public class DeadlineSubmissionController {
    private static final Logger logger = LoggerFactory.getLogger(DeadlineSubmissionController.class);

    @Autowired
    private DeadlineSubmissionService submissionService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> submitAssignment(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("notificationId") String notificationId,
            @RequestParam("studentId") String studentId,
            @RequestParam("comment") String comment,
            @RequestParam("submittedAt") String submittedAt) {
        
        logger.info("=== Starting submission process ===");
        logger.info("Request parameters received:");
        logger.info("- notificationId: {}", notificationId);
        logger.info("- studentId: {}", studentId);
        logger.info("- comment: {}", comment);
        logger.info("- submittedAt: {}", submittedAt);
        
        try {
            // Validate input
            logger.info("Validating input parameters...");
            
            if (notificationId == null || notificationId.trim().isEmpty()) {
                logger.error("Validation failed: Notification ID is empty");
                return ResponseEntity.badRequest().body("Notification ID is required");
            }
            logger.info("Notification ID validation passed");
            
            if (studentId == null || studentId.trim().isEmpty()) {
                logger.error("Validation failed: Student ID is empty");
                return ResponseEntity.badRequest().body("Student ID is required");
            }
            logger.info("Student ID validation passed");
            
            if (submittedAt == null || submittedAt.trim().isEmpty()) {
                logger.error("Validation failed: Submission date is empty");
                return ResponseEntity.badRequest().body("Submission date is required");
            }
            logger.info("Submission date validation passed");

            // Validate file if present
            if (file != null) {
                logger.info("File validation:");
                logger.info("- Original filename: {}", file.getOriginalFilename());
                logger.info("- Content type: {}", file.getContentType());
                logger.info("- Size: {} bytes", file.getSize());
                
                if (file.isEmpty()) {
                    logger.error("Validation failed: File is empty");
                    return ResponseEntity.badRequest().body("File cannot be empty");
                }
                logger.info("File validation passed");
            } else {
                logger.info("No file provided in request");
            }

            // Parse notification ID
            logger.info("Parsing notification ID: {}", notificationId);
            Long parsedNotificationId;
            try {
                parsedNotificationId = Long.parseLong(notificationId);
                logger.info("Successfully parsed notification ID: {}", parsedNotificationId);
            } catch (NumberFormatException e) {
                logger.error("Failed to parse notification ID: {}", notificationId, e);
                return ResponseEntity.badRequest().body("Invalid notification ID format");
            }

            // Prepare files array
            MultipartFile[] files = file != null ? new MultipartFile[]{file} : new MultipartFile[0];
            logger.info("Prepared files array with {} file(s)", files.length);
            
            // Call service
            logger.info("Calling submission service...");
            DeadlineSubmission submission = submissionService.submitAssignment(
                parsedNotificationId,
            studentId,
            files,
            comment,
            submittedAt
            );
            
            logger.info("Submission successful:");
            logger.info("- Submission ID: {}", submission.getId());
            logger.info("- Status: {}", submission.getStatus());
            logger.info("- Submitted files count: {}", submission.getSubmittedFiles() != null ? submission.getSubmittedFiles().size() : 0);
            
            return ResponseEntity.ok(submission);
            
        } catch (Exception e) {
            logger.error("Error during submission process", e);
            logger.error("Error details: {}", e.getMessage());
            if (e.getCause() != null) {
                logger.error("Caused by: {}", e.getCause().getMessage());
            }
            return ResponseEntity.badRequest().body("Error processing submission: " + e.getMessage());
        } finally {
            logger.info("=== Submission process completed ===");
        }
    }

    @PostMapping(value = "/url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeadlineSubmission> submitAssignmentWithUrl(
            @RequestBody SubmissionRequest request) {
        
        return ResponseEntity.ok(submissionService.submitAssignmentWithUrls(
            Long.parseLong(request.getNotificationId()),
            request.getStudentId(),
            request.getFileUrls(),
            request.getComment(),
            request.getSubmittedAt()
        ));
    }

    @PutMapping(value = "/{submissionId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeadlineSubmission> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Double grade,
            @RequestParam(required = false) String feedback) {
        return ResponseEntity.ok(submissionService.gradeSubmission(submissionId, grade, feedback));
    }

    @GetMapping(value = "/student/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeadlineSubmission> getStudentSubmission(
            @PathVariable String studentId,
            @RequestParam Long deadlineId) {
        return ResponseEntity.ok(submissionService.getStudentSubmission(studentId, deadlineId));
    }

    @GetMapping(value = "/deadline/{deadlineId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeadlineSubmission> getAllSubmissions(@PathVariable Long deadlineId) {
        return ResponseEntity.ok(submissionService.getAllSubmissions(deadlineId));
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionDetailResponse> getSubmissionDetails(@PathVariable Long submissionId) {
        return ResponseEntity.ok(submissionService.getSubmissionDetails(submissionId));
    }
} 