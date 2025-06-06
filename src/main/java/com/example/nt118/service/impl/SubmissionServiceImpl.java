package com.example.nt118.service.impl;

import com.example.nt118.dto.*;
import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private DeadlineSubmissionRepository submissionRepository;

    @Autowired
    private SubmissionFileRepository fileRepository;

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public SubmissionResponse submitAssignment(com.example.nt118.dto.SubmissionRequest request, MultipartFile[] files) {
        // Validate deadline
        AssignmentDeadline deadline = deadlineRepository.findById(Long.parseLong(request.getNotificationId()))
                .orElseThrow(() -> new RuntimeException("Deadline not found"));

        // Check if deadline has passed
        if (deadline.getDeadlineDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Deadline has passed");
        }

        // Get student
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Create submission
        DeadlineSubmission submission = new DeadlineSubmission();
        submission.setDeadline(deadline);
        submission.setStudent(student);
        submission.setSubmitDate(request.getSubmittedAt());
        submission.setStatus(DeadlineSubmission.SubmissionStatus.SUBMITTED);
        submission.setFeedback(request.getSubmissionText());

        submission = submissionRepository.save(submission);

        // Handle file uploads
        List<SubmissionFile> submissionFiles = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                try {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.createDirectories(filePath.getParent());
                    Files.copy(file.getInputStream(), filePath);

                    SubmissionFile submissionFile = new SubmissionFile();
                    submissionFile.setSubmission(submission);
                    submissionFile.setFileName(file.getOriginalFilename());
                    submissionFile.setFileUrl("/uploads/" + fileName);
                    submissionFile.setFileType(file.getContentType());
                    submissionFile.setFileSize(file.getSize());
                    submissionFiles.add(fileRepository.save(submissionFile));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file", e);
                }
            }
        }

        // Create response
        SubmissionResponse response = new SubmissionResponse();
        response.setStatus("success");
        response.setMessage("Assignment submitted successfully");

        SubmissionResponse.SubmissionData data = new SubmissionResponse.SubmissionData();
        data.setSubmissionId(submission.getId().toString());
        data.setStatus(submission.getStatus().name());
        data.setGrade(submission.getGrade() != null ? submission.getGrade().floatValue() : null);
        data.setFeedback(submission.getFeedback());
        data.setSubmittedAt(submission.getSubmitDate());
        data.setGradedAt(null);

        response.setData(data);
        return response;
    }

    @Override
    public SubmissionDetailResponse getSubmissionDetail(String submissionId) {
        DeadlineSubmission submission = submissionRepository.findById(Long.parseLong(submissionId))
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        SubmissionDetailResponse response = new SubmissionDetailResponse();
        response.setStatus("success");
        response.setMessage("Submission details retrieved successfully");

        SubmissionDetailResponse.SubmissionDetailData data = new SubmissionDetailResponse.SubmissionDetailData();
        data.setSubmissionId(submission.getId().toString());
        data.setNotificationId(submission.getDeadline().getId().toString());
        data.setCourseId(submission.getDeadline().getCourse().getCourseId());
        data.setStudentId(submission.getStudent().getStudentId());
        data.setSubmissionText(submission.getFeedback());
        data.setStatus(submission.getStatus().name());
        data.setGrade(submission.getGrade() != null ? submission.getGrade().floatValue() : null);
        data.setFeedback(submission.getFeedback());
        data.setSubmittedAt(submission.getSubmitDate());
        data.setGradedAt(null); // Add graded date if available

        // Map submission files
        data.setSubmissionFiles(submission.getSubmittedFiles().stream()
                .map(file -> {
                    SubmissionFileDTO fileDTO = new SubmissionFileDTO();
                    fileDTO.setFileName(file.getFileName());
                    fileDTO.setFileUrl(file.getFileUrl());
                    fileDTO.setFileType(file.getFileType());
                    fileDTO.setFileSize(file.getFileSize());
                    return fileDTO;
                })
                .collect(Collectors.toList()));

        response.setData(data);
        return response;
    }

    @Override
    public FileResponse getSubmissionFile(String submissionId) {
        DeadlineSubmission submission = submissionRepository.findById(Long.parseLong(submissionId))
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        if (submission.getSubmittedFiles().isEmpty()) {
            throw new RuntimeException("No files found for this submission");
        }

        // Get the first file (modify as needed if multiple files should be handled differently)
        SubmissionFile file = submission.getSubmittedFiles().iterator().next();

        FileResponse response = new FileResponse();
        response.setStatus("success");
        response.setMessage("File details retrieved successfully");

        FileResponse.FileData data = new FileResponse.FileData();
        data.setFileId(file.getId().toString());
        data.setFileName(file.getFileName());
        data.setFileUrl(file.getFileUrl());
        data.setFileType(file.getFileType());
        data.setFileSize(file.getFileSize());
        data.setUploadedAt(submission.getSubmitDate());

        response.setData(data);
        return response;
    }
} 