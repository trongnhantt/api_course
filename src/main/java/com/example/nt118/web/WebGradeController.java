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
        
        // Tính lại average
        Double mid = grade.getMidterm() != null ? grade.getMidterm() : 0.0;
        Double prac = grade.getPractice() != null ? grade.getPractice() : 0.0;
        Double fin = grade.getFinalGrade() != null ? grade.getFinalGrade() : 0.0;
        double avg = (mid + prac + fin) / 3.0;
        existingGrade.setAverage(avg);
        
        // Tính lại letterGrade
        String letter;
        if (avg >= 8.5) letter = "A";
        else if (avg >= 7.0) letter = "B";
        else if (avg >= 5.5) letter = "C";
        else if (avg >= 4.0) letter = "D";
        else letter = "F";
        existingGrade.setLetterGrade(letter);
        
        existingGrade.setStatus(grade.getStatus());
        gradeRepository.save(existingGrade);
        return "redirect:/teacher/grades/course/" + courseId;
    }
}