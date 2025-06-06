package com.example.nt118.web;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.model.Course;
import com.example.nt118.repository.AssignmentDeadlineRepository;
import com.example.nt118.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/teacher/deadlines")
public class WebDeadlineController {

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/course/{courseId}")
    public String listDeadlines(@PathVariable String courseId, Model model) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        model.addAttribute("course", course);
        model.addAttribute("deadlines", deadlineRepository.findByCourseId(courseId));
        return "teacher/deadlines/list";
    }

    @GetMapping("/course/{courseId}/new")
    public String newDeadlineForm(@PathVariable String courseId, Model model) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        model.addAttribute("course", course);
        model.addAttribute("deadline", new AssignmentDeadline());
        return "teacher/deadlines/form";
    }

    @PostMapping("/course/{courseId}")
    public String createDeadline(@PathVariable String courseId, @ModelAttribute AssignmentDeadline deadline) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        deadline.setCourse(course);
        deadline.setCreatedAt(LocalDateTime.now());
        deadline.setUpdatedAt(LocalDateTime.now());
        deadlineRepository.save(deadline);
        
        return "redirect:/teacher/deadlines/course/" + courseId;
    }

    @GetMapping("/{id}/edit")
    public String editDeadlineForm(@PathVariable Long id, Model model) {
        AssignmentDeadline deadline = deadlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));
        
        model.addAttribute("course", deadline.getCourse());
        model.addAttribute("deadline", deadline);
        return "teacher/deadlines/form";
    }

    @PostMapping("/{id}")
    public String updateDeadline(@PathVariable Long id, @ModelAttribute AssignmentDeadline deadline) {
        AssignmentDeadline existingDeadline = deadlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));
        
        existingDeadline.setTitle(deadline.getTitle());
        existingDeadline.setDescription(deadline.getDescription());
        existingDeadline.setDeadlineDate(deadline.getDeadlineDate());
        existingDeadline.setSubmissionType(deadline.getSubmissionType());
        existingDeadline.setPriority(deadline.getPriority());
        existingDeadline.setMaxPoints(deadline.getMaxPoints());
        existingDeadline.setWeightPercentage(deadline.getWeightPercentage());
        existingDeadline.setIsGroupWork(deadline.getIsGroupWork());
        existingDeadline.setSubmissionUrl(deadline.getSubmissionUrl());
        existingDeadline.setStatus(deadline.getStatus());
        existingDeadline.setUpdatedAt(LocalDateTime.now());
        
        deadlineRepository.save(existingDeadline);
        return "redirect:/teacher/deadlines/course/" + existingDeadline.getCourse().getCourseId();
    }

    @PostMapping("/{id}/delete")
    public String deleteDeadline(@PathVariable Long id) {
        AssignmentDeadline deadline = deadlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));
        String courseId = deadline.getCourse().getCourseId();
        
        deadlineRepository.delete(deadline);
        return "redirect:/teacher/deadlines/course/" + courseId;
    }
} 