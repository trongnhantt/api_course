package com.example.nt118.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FileResponse {
    private String status;
    private String message;
    private FileData data;

    @Data
    public static class FileData {
        private String fileId;
        private String fileName;
        private String fileUrl;
        private String fileType;
        private Long fileSize;
        private LocalDateTime uploadedAt;
    }
} 