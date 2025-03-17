package com.example.course.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class GetStudentsDTO {
    List<StudentInCreateCourseDTO> students;
    Integer total;
}
