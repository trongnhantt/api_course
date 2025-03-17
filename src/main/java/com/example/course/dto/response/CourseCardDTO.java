package com.example.course.dto.response;

import com.example.course.entity.Subject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class CourseCardDTO {
    private Long courseId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LecturerDTO> lecturers;
    private Long numberOfStudents;
    private Subject subject;
    private Long price;
    private byte[] thumbnail;
    private String subjectName;
    private String description;
    private String duration; // Khoảng thời gian
    private String author; // Tên giảng viên

    // Constructor đầy đủ cho các thuộc tính của CourseCardDTO
    public CourseCardDTO(Long courseId, LocalDateTime createdDate, LocalDateTime updatedDate,
                         LocalDate startDate, LocalDate endDate, Long numberOfStudents,
                         Subject subject, Long price, byte[] thumbnail,
                         String subjectName, String description) {
        this.courseId = courseId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfStudents = numberOfStudents;
        this.subject = subject;
        this.price = price;
        this.thumbnail = thumbnail;
        this.subjectName = subjectName;
        this.description = description;
    }
}