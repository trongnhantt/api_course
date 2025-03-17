package com.example.course.dto.response;

import com.example.course.entity.Subject;
import com.example.course.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class CourseDTO {
    private Long courseId;
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;
    private List<LecturerDTO> lecturers;
//    private List<StudentInCreateCourseDTO> students;
    private List<ScheduleDTO> schedules;
    private Long numberOfStudents;
    private Subject subject;
    public CourseDTO() {}

    public CourseDTO(Long courseId, LocalDateTime createdDate, LocalDateTime updatedDate,
                     LocalDate startDate, LocalDate endDate, Long numberOfStudents, Subject subject) {
        this.courseId = courseId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfStudents = numberOfStudents; // Chuyển Long sang Integer nếu cần
        this.subject = subject;
    }

}
