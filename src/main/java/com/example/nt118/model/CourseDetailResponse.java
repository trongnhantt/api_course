package com.example.nt118.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailResponse {
    private String status;
    private CourseData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseData {
        private Schedule schedule;
        private CourseInfo courseInfo;
        private Grades grades;
        private Attendance attendance;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {
        private String dayOfWeek;
        private String startTime;
        private String endTime;
        private String room;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseInfo {
        private String id;
        private String name;
        private String instructor;
        private int credit;
        private String room;
        private String schedule;
        private String description;
        private String department;
        private String semester;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grades {
        private Double midterm;
        private Double practice;
        private Double finalGrade;
        private Double average;
        private String letterGrade;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attendance {
        private int totalSessions;
        private int attendedSessions;
        private int absentSessions;
        private double attendanceRate;
    }
} 