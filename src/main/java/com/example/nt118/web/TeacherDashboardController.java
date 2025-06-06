package com.example.nt118.web;

import com.example.nt118.model.Course;
import com.example.nt118.model.Teacher;
import com.example.nt118.repository.CourseRepository;
import com.example.nt118.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherDashboardController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // TODO: Get teacher ID from session/security context
        String teacherId = "T001";
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        List<Course> courses = courseRepository.findByTeacherId(teacherId);

        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courses);
        return "teacher/dashboard";
    }
} 