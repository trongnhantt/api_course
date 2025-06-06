package com.example.nt118.model;

import lombok.Data;

@Data
public class StudentProfileResponse {
    private String status;
    private StudentProfileData data;

    @Data
    public static class StudentProfileData {
        private String studentId;
        private String name;
        private String email;
        private String className;
        private String dateOfBirth;
        private String avatarUrl;
        private Statistics statistics;
    }

    @Data
    public static class Statistics {
        private int totalCourses;
        private int totalAttendance;
        private int totalGrades;
        private double tuitionBalance;
        private GradeDistribution gradeDistribution;
    }

    @Data
    public static class GradeDistribution {
        private int gradeA;
        private int gradeB;
        private int gradeC;
        private int gradeD;
        private double gpa;
    }
} 