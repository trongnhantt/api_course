package com.example.nt118.web;

import com.example.nt118.model.Course;
import com.example.nt118.model.Notification;
import com.example.nt118.model.NotificationType;
import com.example.nt118.service.NotificationService;
import com.example.nt118.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/teacher/notifications")
public class WebNotificationController {
    private static final Logger logger = LoggerFactory.getLogger(WebNotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/course/{courseId}")
    public String listNotifications(@PathVariable String courseId, Model model) {
        try {
            Course course = courseService.getCourseById(courseId);
            List<Notification> notifications = notificationService.getNotificationsByCourseId(courseId);
            
            model.addAttribute("course", course);
            model.addAttribute("notifications", notifications);
            
            return "teacher/notifications/list";
        } catch (Exception e) {
            logger.error("Error listing notifications for course {}: {}", courseId, e.getMessage());
            model.addAttribute("error", "Không thể tải danh sách thông báo");
            return "error";
        }
    }

    @GetMapping("/new")
    public String showNewForm(@RequestParam String courseId, Model model) {
        try {
            Course course = courseService.getCourseById(courseId);
            Notification notification = new Notification();
            notification.setCourse(course);
            notification.setStatus("ACTIVE");
            notification.setType(NotificationType.ANNOUNCEMENT);
            
            model.addAttribute("course", course);
            model.addAttribute("notification", notification);
            
            return "teacher/notifications/form";
        } catch (Exception e) {
            logger.error("Error showing new notification form for course {}: {}", courseId, e.getMessage());
            model.addAttribute("error", "Không thể tải form thêm thông báo");
            return "error";
        }
    }

    @PostMapping("/course/{courseId}")
    public String createNotification(@PathVariable String courseId, @ModelAttribute Notification notification, Model model) {
        try {
            Course course = courseService.getCourseById(courseId);
            notification.setCourse(course);
            notificationService.createNotification(notification);
            
            return "redirect:/teacher/notifications/course/" + courseId;
        } catch (Exception e) {
            logger.error("Error creating notification for course {}: {}", courseId, e.getMessage());
            model.addAttribute("error", "Không thể tạo thông báo mới");
            model.addAttribute("course", courseService.getCourseById(courseId));
            model.addAttribute("notification", notification);
            return "teacher/notifications/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            Course course = notification.getCourse();
            
            model.addAttribute("course", course);
            model.addAttribute("notification", notification);
            
            return "teacher/notifications/form";
        } catch (Exception e) {
            logger.error("Error showing edit form for notification {}: {}", id, e.getMessage());
            model.addAttribute("error", "Không thể tải form sửa thông báo");
            return "error";
        }
    }

    @PostMapping("/{id}")
    public String updateNotification(@PathVariable Long id, @ModelAttribute Notification notification, Model model) {
        try {
            Notification existingNotification = notificationService.getNotificationById(id);
            notification.setCourse(existingNotification.getCourse());
            notificationService.updateNotification(id, notification);
            
            return "redirect:/teacher/notifications/course/" + existingNotification.getCourse().getCourseId();
        } catch (Exception e) {
            logger.error("Error updating notification {}: {}", id, e.getMessage());
            model.addAttribute("error", "Không thể cập nhật thông báo");
            model.addAttribute("course", notification.getCourse());
            model.addAttribute("notification", notification);
            return "teacher/notifications/form";
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteNotification(@PathVariable Long id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            String courseId = notification.getCourse().getCourseId();
            notificationService.deleteNotification(id);
            return "success";
        } catch (Exception e) {
            logger.error("Error deleting notification {}: {}", id, e.getMessage());
            return "error";
        }
    }
} 