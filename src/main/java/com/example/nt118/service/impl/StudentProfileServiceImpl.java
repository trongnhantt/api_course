package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentProfileServiceImpl implements StudentProfileService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CourseGradeRepository courseGradeRepository;

    @Override
    public StudentProfileResponse getStudentProfile(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentProfileResponse response = new StudentProfileResponse();
        response.setStatus("success");

        StudentProfileResponse.StudentProfileData data = new StudentProfileResponse.StudentProfileData();
        data.setStudentId(student.getStudentId());
        data.setName(student.getName());
        data.setEmail(student.getEmail());
        data.setClassName(student.getClassName());
        data.setDateOfBirth(student.getDob().toString());
        data.setAvatarUrl(student.getAvatarUrl());

        // Get statistics
        StudentProfileResponse.Statistics statistics = new StudentProfileResponse.Statistics();
        
        // Get total courses
        List<Course> courses = courseRepository.findByStudentId(studentId);
        statistics.setTotalCourses(courses.size());

        // Get total attendance
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        statistics.setTotalAttendance(attendances.size());

        // Get total grades
        List<CourseGrade> grades = courseGradeRepository.findBySemesterIdAndStudentIdWithTeacher("HK1_2024", studentId);
        statistics.setTotalGrades(grades.size());

        // Calculate grade distribution
        StudentProfileResponse.GradeDistribution gradeDistribution = new StudentProfileResponse.GradeDistribution();
        Map<String, Long> gradeCounts = grades.stream()
                .collect(Collectors.groupingBy(CourseGrade::getLetterGrade, Collectors.counting()));
        
        gradeDistribution.setGradeA(gradeCounts.getOrDefault("A", 0L).intValue());
        gradeDistribution.setGradeB(gradeCounts.getOrDefault("B+", 0L).intValue() + gradeCounts.getOrDefault("B", 0L).intValue());
        gradeDistribution.setGradeC(gradeCounts.getOrDefault("C+", 0L).intValue() + gradeCounts.getOrDefault("C", 0L).intValue());
        gradeDistribution.setGradeD(gradeCounts.getOrDefault("D+", 0L).intValue() + gradeCounts.getOrDefault("D", 0L).intValue());
        
        // Calculate GPA
        double totalPoints = grades.stream()
                .mapToDouble(grade -> {
                    switch (grade.getLetterGrade()) {
                        case "A": return 4.0;
                        case "B+": return 3.5;
                        case "B": return 3.0;
                        case "C+": return 2.5;
                        case "C": return 2.0;
                        case "D+": return 1.5;
                        case "D": return 1.0;
                        default: return 0.0;
                    }
                })
                .sum();
        gradeDistribution.setGpa(grades.isEmpty() ? 0.0 : Math.round(totalPoints / grades.size() * 100.0) / 100.0);

        statistics.setGradeDistribution(gradeDistribution);
        
        // Calculate tuition balance
        List<Course> currentCourses = courseRepository.findByStudentIdAndSemester(studentId, "HK1_2024");
        double tuitionBalance = currentCourses.stream()
                .mapToDouble(course -> course.getCredits() * 1000000.0) // 1 credit = 1,000,000 VND
                .sum();
        statistics.setTuitionBalance(tuitionBalance);

        data.setStatistics(statistics);
        response.setData(data);

        return response;
    }
} 