package com.example.course.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long studentId;
    private Long userId;
    private String username;
    private String email;
    private Boolean gender; // Female true - Male fale
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private List<StudentCourseResponse> studentCourse;
}
