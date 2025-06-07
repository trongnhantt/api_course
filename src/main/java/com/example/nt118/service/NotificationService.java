package com.example.nt118.service;

import com.example.nt118.model.Notification;
import com.example.nt118.model.NotificationResponse;
import java.util.List;

public interface NotificationService {
    NotificationResponse getCourseNotifications(String courseId, String studentId);
    List<Notification> getNotificationsByCourseId(String courseId);
    Notification getNotificationById(Long id);
    Notification createNotification(Notification notification);
    Notification updateNotification(Long id, Notification notification);
    void deleteNotification(Long id);
} 