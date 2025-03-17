package com.example.course.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseResponse {
    private Long courseId;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;
}
