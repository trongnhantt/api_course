package com.example.nt118.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationResponse {
    private boolean success;
    private String message;
    private List<NotificationDTO> notifications;
    private long totalUnread;

    @Data
    public static class NotificationDTO {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private String courseId;
        private NotificationType type;
        private boolean isRead;
    }
} 