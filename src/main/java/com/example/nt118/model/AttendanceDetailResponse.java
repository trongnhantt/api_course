package com.example.nt118.model;

import lombok.Data;
import java.util.List;

@Data
public class AttendanceDetailResponse {
    private String courseId;
    private String courseName;
    private int totalSessions;
    private int attendedSessions;
    private double attendanceRate;
    private List<SessionAttendance> sessions;

    @Data
    public static class SessionAttendance {
        private String sessionId;
        private String date;
        private String time;
        private String status;
        private String note;
    }
} 