package com.example.nt118.model;

import lombok.Data;
import java.util.List;

@Data
public class AttendanceResponse {
    private String status;
    private AttendanceData data;

    @Data
    public static class AttendanceData {
        private int totalAttendance;
        private double attendanceRate;
        private List<CourseAttendance> courses;
    }

    @Data
    public static class CourseAttendance {
        private String courseId;
        private String courseName;
        private int totalSessions;
        private int attendedSessions;
        private double attendanceRate;
        private List<SessionAttendance> sessions;
    }

    @Data
    public static class SessionAttendance {
        private String sessionId;
        private String date;
        private String status; // present, absent, late
        private String note;
    }
} 