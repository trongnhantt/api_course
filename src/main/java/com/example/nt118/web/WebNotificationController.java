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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            return "redirect:/teacher/dashboard";
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
            return "redirect:/teacher/notifications/course/" + courseId;
        }
    }

    @PostMapping("/course/{courseId}")
    public String createNotification(@PathVariable String courseId, 
                                   @ModelAttribute Notification notification, 
                                   RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.getCourseById(courseId);
            notification.setCourse(course);
            notification.setCreatedAt(LocalDateTime.now());
            notification.setReadBy(new ArrayList<>());
            notificationService.createNotification(notification);
            
            redirectAttributes.addFlashAttribute("success", "Thông báo đã được tạo thành công");
            return "redirect:/teacher/notifications/course/" + courseId;
        } catch (Exception e) {
            logger.error("Error creating notification for course {}: {}", courseId, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Không thể tạo thông báo mới: " + e.getMessage());
            return "redirect:/teacher/notifications/course/" + courseId;
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
            return "redirect:/teacher/dashboard";
        }
    }

    @PostMapping("/{id}")
    public String updateNotification(@PathVariable Long id, 
                                   @ModelAttribute Notification notification,
                                   RedirectAttributes redirectAttributes) {
        try {
            Notification existingNotification = notificationService.getNotificationById(id);
            notification.setCourse(existingNotification.getCourse());
            notificationService.updateNotification(id, notification);
            
            redirectAttributes.addFlashAttribute("success", "Thông báo đã được cập nhật thành công");
            return "redirect:/teacher/notifications/course/" + existingNotification.getCourse().getCourseId();
        } catch (Exception e) {
            logger.error("Error updating notification {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Không thể cập nhật thông báo: " + e.getMessage());
            return "redirect:/teacher/notifications/course/" + notification.getCourse().getCourseId();
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