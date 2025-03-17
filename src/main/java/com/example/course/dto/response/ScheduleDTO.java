package com.example.course.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class ScheduleDTO {
    private Long scheduleId;
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private RoomDTO room;

    public ScheduleDTO(Long scheduleId, LocalDateTime createdDate,
                       LocalDateTime updatedDate, LocalDateTime startTime,
                       LocalDateTime endTime, RoomDTO room) {
        this.scheduleId = scheduleId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }
}
