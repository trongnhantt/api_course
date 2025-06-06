package com.example.nt118.model;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AcademicReportResponse {
    private String status;
    private AcademicReportData data;

    @Data
    public static class AcademicReportData {
        private SemesterInfo semester;
        private List<CourseInfo> courses;
        private Map<String, Integer> gradeDistribution;
        private AcademicProgressInfo academicProgress;
    }

    @Data
    public static class SemesterInfo {
        private String id;
        private String name;
        private SemesterSummary summary;
    }

    @Data
    public static class SemesterSummary {
        private Double gpa;
        private Integer totalCredits;
        private Integer completedCourses;
        private Integer inProgressCourses;
    }

    @Data
    public static class CourseInfo {
        private String courseId;
        private String courseName;
        private Integer credits;
        private String description;
        private String department;
        private String room;
        private String schedule;
        private String semester;
        private String status;
        private TeacherInfo teacher;
        private GradeInfo grades;
    }

    @Data
    public static class GradeInfo {
        private Double midterm;
        private Double practice;
        private Double finalGrade;
        private Double average;
        private String letterGrade;
    }

    @Data
    public static class TeacherInfo {
        private String teacherId;
        private String name;
        private String email;
        private String phone;
        private String department;
        private String position;
        private String avatarUrl;
    }

    @Data
    public static class AcademicProgressInfo {
        private String studentId;
        private Integer totalRequiredCredits;
        private Integer completedCredits;
        private Integer remainingCredits;
        private String estimatedGraduation;
        private StudentInfo student;
    }

    @Data
    public static class StudentInfo {
        private String studentId;
        private String name;
        private String email;
        private String avatarUrl;
        private String className;
        private String dob;
    }
} 