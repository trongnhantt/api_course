package com.example.course.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class LecturerDTO {
    private Long lecturerId;
    private String username;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastAccess;
    private Boolean gender; // Female true - Male false
    private byte[] avatar;

    // Constructor với tất cả các tham số
    public LecturerDTO(Long lecturerId, String username, String email, LocalDateTime lastAccess, Boolean gender, byte[] avatar) {
        this.lecturerId = lecturerId;
        this.username = username;
        this.email = email;
        this.lastAccess = lastAccess;
        this.gender = gender;
        this.avatar = avatar;
    }
}
