package com.example.nt118.dto;

import lombok.Data;

@Data
public class SubmissionFileDTO {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
} 