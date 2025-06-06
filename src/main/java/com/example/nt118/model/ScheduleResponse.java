package com.example.nt118.model;

import lombok.Data;
import java.util.List;

@Data
public class ScheduleResponse {
    private String status;
    private String message;
    private ScheduleData data;

    @Data
    public static class ScheduleData {
        private String date;
        private List<ScheduleItem> schedule;
    }

    @Data
    public static class ScheduleItem {
        private String startTime;
        private String endTime;
        private String subjectName;
        private String room;
        private String courseId;
        private TeacherInfo teacher;
    }

    @Data
    public static class TeacherInfo {
        private String name;
        private String email;
    }
} 