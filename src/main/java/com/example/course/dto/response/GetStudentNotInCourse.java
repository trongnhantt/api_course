package com.example.course.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class GetStudentNotInCourse {
    List<StudentNotInCourseDTO> students;
    Integer total;
}
