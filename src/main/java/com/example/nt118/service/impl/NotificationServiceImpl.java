package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public NotificationResponse getCourseNotifications(String courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Notification> notifications = notificationRepository.findByCourseOrderByCreatedAtDesc(course);
        long totalUnread = notificationRepository.countUnreadByCourseAndStudent(course, studentId);

        List<NotificationResponse.NotificationDTO> notificationDTOs = notifications.stream()
                .map(notification -> {
                    NotificationResponse.NotificationDTO dto = new NotificationResponse.NotificationDTO();
                    dto.setId(notification.getId());
                    dto.setTitle(notification.getTitle());
                    dto.setContent(notification.getContent());
                    dto.setCreatedAt(notification.getCreatedAt());
                    dto.setCourseId(notification.getCourse().getCourseId());
                    dto.setType(notification.getType());
                    dto.setRead(notification.getReadBy().contains(student));
                    return dto;
                })
                .collect(Collectors.toList());

        NotificationResponse response = new NotificationResponse();
        response.setSuccess(true);
        response.setMessage("Success");
        response.setNotifications(notificationDTOs);
        response.setTotalUnread(totalUnread);

        return response;
    }

    @Override
    public List<Notification> getNotificationsByCourseId(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return notificationRepository.findByCourseOrderByCreatedAtDesc(course);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    @Transactional
    public Notification createNotification(Notification notification) {
        Course course = courseRepository.findById(notification.getCourse().getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        notification.setCourse(course);
        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public Notification updateNotification(Long id, Notification notification) {
        Notification existingNotification = getNotificationById(id);
        existingNotification.setTitle(notification.getTitle());
        existingNotification.setContent(notification.getContent());
        existingNotification.setType(notification.getType());
        return notificationRepository.save(existingNotification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
} 