package com.example.course.mapper;

import com.example.course.dto.SubjectDTO;
import com.example.course.entity.Subject;

public class SubjectMapper {

    public static SubjectDTO toSubjectDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectName(subject.getSubjectName());
        dto.setDescription(subject.getDescription());
        dto.setCreatedDate(subject.getCreatedDate());
        dto.setUpdatedDate(subject.getUpdatedDate());
        dto.setCategoryName(subject.getCategory() != null ? subject.getCategory().getCategoryName() : null);
        return dto;
    }
}
