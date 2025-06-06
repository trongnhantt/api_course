package com.example.nt118.service;

import com.example.nt118.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface SubmissionService {
    SubmissionResponse submitAssignment(SubmissionRequest request, MultipartFile[] files);
    SubmissionDetailResponse getSubmissionDetail(String submissionId);
    FileResponse getSubmissionFile(String submissionId);
} 