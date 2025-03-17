package com.example.course.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class GetSubjectsDTO {
    List<SubjectInCreateCourseDTO> subjects;
    Integer total;
}
