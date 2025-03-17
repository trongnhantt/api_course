package com.example.course.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class ScheduleRequestDTO {
    private String startTime;
    private String endTime;
    private Long roomId;
}
