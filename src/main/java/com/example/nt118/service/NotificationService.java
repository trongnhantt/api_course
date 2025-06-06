package com.example.nt118.service;

import com.example.nt118.model.NotificationResponse;

public interface NotificationService {
    NotificationResponse getCourseNotifications(String courseId, String studentId);
} 