package com.example.nt118.controller;

import com.example.nt118.model.DeadlineAttachment;
import com.example.nt118.service.DeadlineAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/deadlines/attachments")
public class DeadlineAttachmentController {

    @Autowired
    private DeadlineAttachmentService attachmentService;

    @PostMapping("/{deadlineId}/upload")
    public ResponseEntity<DeadlineAttachment> uploadFile(
            @PathVariable Long deadlineId,
            @RequestParam("file") MultipartFile file) {
        DeadlineAttachment attachment = attachmentService.storeFile(deadlineId, file);
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/{attachmentId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long attachmentId) {
        Resource resource = attachmentService.loadFileAsResource(attachmentId);
        String contentType = "application/octet-stream";
        String filename = resource.getFilename();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long attachmentId) {
        attachmentService.deleteFile(attachmentId);
        return ResponseEntity.ok().build();
    }
} 