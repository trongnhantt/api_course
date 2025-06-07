package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.DeadlineSubmissionService;
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
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeadlineSubmissionServiceImpl implements DeadlineSubmissionService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private DeadlineSubmissionRepository submissionRepository;

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubmissionFileRepository fileRepository;

    @Override
    @Transactional
    public DeadlineSubmission submitAssignment(Long notificationId, String studentId, MultipartFile[] files, String comment, String submittedAt) {
        AssignmentDeadline deadline = deadlineRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        DeadlineSubmission submission = submissionRepository
                .findByDeadlineAndStudent(deadline, student)
                .orElse(new DeadlineSubmission());

        submission.setDeadline(deadline);
        submission.setStudent(student);
        submission.setSubmitDate(LocalDateTime.parse(submittedAt, DateTimeFormatter.ISO_DATE_TIME));
        submission.setStatus(DeadlineSubmission.SubmissionStatus.SUBMITTED);
        submission.setFeedback(comment);

        Set<SubmissionFile> submissionFiles = new HashSet<>();

        // Handle file uploads
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                try {
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path uploadPath = Paths.get(uploadDir);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath);

                    SubmissionFile submissionFile = new SubmissionFile();
                    submissionFile.setSubmission(submission);
                    submissionFile.setFileName(file.getOriginalFilename());
                    submissionFile.setFileType(file.getContentType());
                    submissionFile.setFileSize(file.getSize());
                    submissionFile.setFileUrl(fileName);
                    submissionFiles.add(submissionFile);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
                }
            }
        }

        submission.setSubmittedFiles(submissionFiles);
        return submissionRepository.save(submission);
    }

    @Override
    @Transactional
    public DeadlineSubmission submitAssignmentWithUrls(Long notificationId, String studentId, List<String> fileUrls, String comment, String submittedAt) {
        AssignmentDeadline deadline = deadlineRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        DeadlineSubmission submission = submissionRepository
                .findByDeadlineAndStudent(deadline, student)
                .orElse(new DeadlineSubmission());

        submission.setDeadline(deadline);
        submission.setStudent(student);
        submission.setSubmitDate(LocalDateTime.parse(submittedAt, DateTimeFormatter.ISO_DATE_TIME));
        submission.setStatus(DeadlineSubmission.SubmissionStatus.SUBMITTED);
        submission.setFeedback(comment);

        Set<SubmissionFile> submissionFiles = new HashSet<>();

        // Handle file URLs
        if (fileUrls != null && !fileUrls.isEmpty()) {
            for (String fileUrl : fileUrls) {
                SubmissionFile submissionFile = new SubmissionFile();
                submissionFile.setSubmission(submission);
                submissionFile.setFileName(fileUrl.substring(fileUrl.lastIndexOf("/") + 1));
                submissionFile.setFileType("application/octet-stream");
                submissionFile.setFileSize(0L);
                submissionFile.setFileUrl(fileUrl);
                submissionFiles.add(submissionFile);
            }
        }

        submission.setSubmittedFiles(submissionFiles);
        return submissionRepository.save(submission);
    }

    @Override
    @Transactional
    public DeadlineSubmission gradeSubmission(Long submissionId, Double grade, String feedback) {
        DeadlineSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        submission.setGrade(grade);
        submission.setFeedback(feedback);
        submission.setStatus(DeadlineSubmission.SubmissionStatus.GRADED);

        return submissionRepository.save(submission);
    }

    @Override
    public DeadlineSubmission getStudentSubmission(String studentId, Long deadlineId) {
        AssignmentDeadline deadline = deadlineRepository.findById(deadlineId)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return submissionRepository.findByDeadlineAndStudent(deadline, student)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    @Override
    public DeadlineSubmission getAllSubmissions(Long deadlineId) {
        AssignmentDeadline deadline = deadlineRepository.findById(deadlineId)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));

        return submissionRepository.findByDeadline(deadline)
                .orElseThrow(() -> new RuntimeException("No submissions found"));
    }

    @Override
    public SubmissionDetailResponse getSubmissionDetails(Long submissionId) {
        DeadlineSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        SubmissionDetailResponse response = new SubmissionDetailResponse();
        response.setStatus("success");

        SubmissionDetailResponse.SubmissionData data = new SubmissionDetailResponse.SubmissionData();
        data.setId(submission.getId());
        data.setSubmitDate(submission.getSubmitDate());
        data.setGrade(submission.getGrade());
        data.setFeedback(submission.getFeedback());
        data.setStatus(submission.getStatus().name());

        // Map student info
        SubmissionDetailResponse.StudentInfo studentInfo = new SubmissionDetailResponse.StudentInfo();
        studentInfo.setStudentId(submission.getStudent().getStudentId());
        studentInfo.setName(submission.getStudent().getName());
        studentInfo.setEmail(submission.getStudent().getEmail());
        studentInfo.setClassName(submission.getStudent().getClassName());
        data.setStudent(studentInfo);

        // Map submitted files
        data.setSubmittedFiles(submission.getSubmittedFiles().stream()
                .map(file -> {
                    SubmissionDetailResponse.SubmittedFile submittedFile = new SubmissionDetailResponse.SubmittedFile();
                    submittedFile.setId(file.getId());
                    submittedFile.setFileName(file.getFileName());
                    submittedFile.setFileUrl(file.getFileUrl());
                    submittedFile.setFileType(file.getFileType());
                    submittedFile.setFileSize(file.getFileSize());
                    return submittedFile;
                })
                .collect(Collectors.toList()));

        response.setData(data);
        return response;
    }

    @Override
    public List<DeadlineSubmission> getSubmissionsByDeadlineId(Long deadlineId) {
        return submissionRepository.findByDeadlineId(deadlineId);
    }
} 