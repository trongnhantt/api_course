package com.example.nt118.service.impl;

import com.example.nt118.model.Schedule;
import com.example.nt118.model.ScheduleResponse;
import com.example.nt118.repository.ScheduleRepository;
import com.example.nt118.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponse getStudentScheduleByDate(String studentId, String date) {
        ScheduleResponse response = new ScheduleResponse();
        ScheduleResponse.ScheduleData data = new ScheduleResponse.ScheduleData();
        List<ScheduleResponse.ScheduleItem> scheduleItems = new ArrayList<>();

        try {
            // Parse date and get day of week
            LocalDate localDate = LocalDate.parse(date);
            String dayOfWeek = localDate.getDayOfWeek().toString();

            // Get schedules for the student on this day
            List<Schedule> schedules = scheduleRepository.findByStudentIdAndDayOfWeek(studentId, dayOfWeek);

            // Convert schedules to response format
            for (Schedule schedule : schedules) {
                ScheduleResponse.ScheduleItem item = new ScheduleResponse.ScheduleItem();
                item.setStartTime(schedule.getStartTime().toString().substring(0, 5));
                item.setEndTime(schedule.getEndTime().toString().substring(0, 5));
                item.setSubjectName(schedule.getCourse().getCourseName());
                item.setRoom(schedule.getRoom());
                item.setCourseId(schedule.getCourse().getCourseId());

                ScheduleResponse.TeacherInfo teacherInfo = new ScheduleResponse.TeacherInfo();
                teacherInfo.setName(schedule.getCourse().getTeacher().getName());
                teacherInfo.setEmail(schedule.getCourse().getTeacher().getEmail());
                item.setTeacher(teacherInfo);

                scheduleItems.add(item);
            }

            // Sort schedule items by start time
            scheduleItems.sort((a, b) -> a.getStartTime().compareTo(b.getStartTime()));

            data.setDate(date);
            data.setSchedule(scheduleItems);

            response.setStatus("success");
            response.setMessage("Retrieved schedule successfully");
            response.setData(data);

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage("Error retrieving schedule: " + e.getMessage());
        }

        return response;
    }
} 