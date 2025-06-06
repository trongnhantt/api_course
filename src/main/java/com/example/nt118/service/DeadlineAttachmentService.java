package com.example.nt118.service;

import com.example.nt118.model.DeadlineAttachment;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DeadlineAttachmentService {
    DeadlineAttachment storeFile(Long deadlineId, MultipartFile file);
    Resource loadFileAsResource(Long attachmentId);
    void deleteFile(Long attachmentId);
} 