package com.example.course.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class GetUserStudentResponse {
    private Long studentId;
    private Long userId;
    private String username;
    private String email;
    private Boolean gender; // Female true - Male fale
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
}
