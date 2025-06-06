package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.AttendanceRepository;
import com.example.nt118.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public AttendanceResponse getStudentAttendance(String studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        return createAttendanceResponse(attendances);
    }

    @Override
    public AttendanceResponse getStudentCourseAttendance(String studentId, String courseId) {
        List<Attendance> attendances = attendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
        return createAttendanceResponse(attendances);
    }

    private AttendanceResponse createAttendanceResponse(List<Attendance> attendances) {
        AttendanceResponse response = new AttendanceResponse();
        response.setStatus("success");

        AttendanceResponse.AttendanceData data = new AttendanceResponse.AttendanceData();
        
        // Group attendances by course
        Map<Course, List<Attendance>> courseAttendances = attendances.stream()
            .collect(Collectors.groupingBy(Attendance::getCourse));

        List<AttendanceResponse.CourseAttendance> courseAttendanceList = new ArrayList<>();
        int totalAttendance = 0;
        int totalAttended = 0;

        for (Map.Entry<Course, List<Attendance>> entry : courseAttendances.entrySet()) {
            Course course = entry.getKey();
            List<Attendance> courseAttendance = entry.getValue();

            AttendanceResponse.CourseAttendance courseAttendanceData = new AttendanceResponse.CourseAttendance();
            courseAttendanceData.setCourseId(course.getCourseId());
            courseAttendanceData.setCourseName(course.getCourseName());
            courseAttendanceData.setTotalSessions(courseAttendance.size());

            int attendedSessions = (int) courseAttendance.stream()
                .filter(a -> a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                .count();
            courseAttendanceData.setAttendedSessions(attendedSessions);

            double attendanceRate = (double) attendedSessions / courseAttendance.size() * 100;
            courseAttendanceData.setAttendanceRate(Math.round(attendanceRate * 10.0) / 10.0);

            // Map sessions
            List<AttendanceResponse.SessionAttendance> sessions = courseAttendance.stream()
                .map(attendance -> {
                    AttendanceResponse.SessionAttendance session = new AttendanceResponse.SessionAttendance();
                    session.setSessionId(attendance.getId().toString());
                    session.setDate(attendance.getSessionDate().toString());
                    session.setStatus(attendance.getStatus().name().toLowerCase());
                    session.setNote(attendance.getNote());
                    return session;
                })
                .collect(Collectors.toList());
            courseAttendanceData.setSessions(sessions);

            courseAttendanceList.add(courseAttendanceData);
            totalAttendance += courseAttendance.size();
            totalAttended += attendedSessions;
        }

        data.setTotalAttendance(totalAttendance);
        double overallAttendanceRate = totalAttendance > 0 ? 
            (double) totalAttended / totalAttendance * 100 : 0;
        data.setAttendanceRate(Math.round(overallAttendanceRate * 10.0) / 10.0);
        data.setCourses(courseAttendanceList);

        response.setData(data);
        return response;
    }
} 