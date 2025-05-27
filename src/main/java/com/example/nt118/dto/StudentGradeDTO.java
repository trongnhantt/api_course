package com.example.nt118.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeDTO {
    private String courseName;
    private String instructor;
    private int credits;
    private String courseId;
    private float finalGrade;
} 