package com.example.nt118.service.impl;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.model.DeadlineAttachment;
import com.example.nt118.repository.AssignmentDeadlineRepository;
import com.example.nt118.repository.DeadlineAttachmentRepository;
import com.example.nt118.service.DeadlineAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DeadlineAttachmentServiceImpl implements DeadlineAttachmentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private DeadlineAttachmentRepository attachmentRepository;

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Override
    public DeadlineAttachment storeFile(Long deadlineId, MultipartFile file) {
        AssignmentDeadline deadline = deadlineRepository.findById(deadlineId)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new RuntimeException("Invalid file path sequence " + fileName);
            }

            Path targetLocation = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            DeadlineAttachment attachment = new DeadlineAttachment();
            attachment.setDeadline(deadline);
            attachment.setFileName(fileName);
            attachment.setFileUrl("/api/deadlines/attachments/" + fileName);
            attachment.setFileType(file.getContentType());
            attachment.setFileSize(file.getSize());

            return attachmentRepository.save(attachment);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(Long attachmentId) {
        try {
            DeadlineAttachment attachment = attachmentRepository.findById(attachmentId)
                    .orElseThrow(() -> new RuntimeException("File not found"));

            Path filePath = Paths.get(uploadDir).resolve(attachment.getFileName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + attachment.getFileName());
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found", ex);
        }
    }

    @Override
    public void deleteFile(Long attachmentId) {
        DeadlineAttachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Path filePath = Paths.get(uploadDir).resolve(attachment.getFileName()).normalize();
        try {
            Files.deleteIfExists(filePath);
            attachmentRepository.delete(attachment);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file", ex);
        }
    }
} 