package com.example.course.dto.request;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CreateCourseRequest {
    private String startDate;
    private String endDate;
    private Long subjectId;
    private List<Long> lecturerIds;
    private List<Long> studentIds;
    private List<ScheduleRequestDTO> schedules;
}
