package com.example.course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class GetCourseCardDTO {
    List<CourseCardDTO> courses;
    Integer total;
}
