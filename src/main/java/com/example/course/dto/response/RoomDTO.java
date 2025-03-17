package com.example.course.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class RoomDTO {
    private Long roomId;
    private String roomName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
    public RoomDTO(){}
    public RoomDTO(Long roomId, String roomName, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
