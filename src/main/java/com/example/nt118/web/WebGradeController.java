package com.example.nt118.web;

import com.example.nt118.model.Course;
import com.example.nt118.model.CourseGrade;
import com.example.nt118.model.Student;
import com.example.nt118.repository.CourseGradeRepository;
import com.example.nt118.repository.CourseRepository;
import com.example.nt118.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher/grades")
public class WebGradeController {

    @Autowired
    private CourseGradeRepository gradeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/course/{courseId}")
    public String listGrades(@PathVariable String courseId, Model model) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        List<CourseGrade> grades = gradeRepository.findByCourseId(courseId);
        
        model.addAttribute("course", course);
        model.addAttribute("grades", grades);
        return "teacher/grades/list";
    }

    @GetMapping("/course/{courseId}/student/{studentId}")
    public String editGradeForm(@PathVariable String courseId, @PathVariable String studentId, Model model) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        CourseGrade grade = gradeRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElse(new CourseGrade());
        
        model.addAttribute("course", course);
        model.addAttribute("student", student);
        model.addAttribute("grade", grade);
        return "teacher/grades/form";
    }

    @PostMapping("/course/{courseId}/student/{studentId}")
    public String updateGrade(@PathVariable String courseId, @PathVariable String studentId, @ModelAttribute CourseGrade grade) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        CourseGrade existingGrade = gradeRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElse(new CourseGrade());
        
        existingGrade.setCourse(course);
        existingGrade.setStudent(student);
        existingGrade.setMidterm(grade.getMidterm());
        existingGrade.setPractice(grade.getPractice());
        existingGrade.setFinalGrade(grade.getFinalGrade());
        existingGrade.setAverage(grade.getAverage());
        existingGrade.setLetterGrade(grade.getLetterGrade());
        existingGrade.setStatus(grade.getStatus());
        
        gradeRepository.save(existingGrade);
        return "redirect:/teacher/grades/course/" + courseId;
    }
} 