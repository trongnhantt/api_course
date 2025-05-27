package com.example.nt118.service;

import com.example.nt118.model.Course;
import com.example.nt118.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> getCoursesByStudentId(String studentId) {
        return courseRepository.findByStudentId(studentId);
    }
    
    public List<Course> getStudentCoursesByStatus(String studentId, String status) {
        return courseRepository.findByStudentIdAndStatus(studentId, status);
    }
    
    public List<Course> getStudentCoursesBySemester(String studentId, String semester) {
        return courseRepository.findByStudentIdAndSemester(studentId, semester);
    }
    
    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
} 