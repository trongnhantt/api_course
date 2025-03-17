package com.example.course.mapper.custom;

import com.example.course.dto.response.LecturerInCreateCourseDTO;
import com.example.course.entity.Lecturer;

public class LecturerMapper {
    public static LecturerInCreateCourseDTO mapLecturerToLecturerDTO(Lecturer lecturer) {
        if (lecturer == null || lecturer.getUser() == null) {
            return null; // Kiểm tra null để tránh lỗi NullPointerException
        }

        LecturerInCreateCourseDTO lecturerDTO = new LecturerInCreateCourseDTO();
        lecturerDTO.setLecturerId(lecturer.getLecturerId());
        lecturerDTO.setUserId(lecturer.getUser().getUserId());
        lecturerDTO.setUsername(lecturer.getUser().getUsername());
        lecturerDTO.setEmail(lecturer.getUser().getEmail());
        lecturerDTO.setGender(lecturer.getUser().getGender());
        lecturerDTO.setDob(lecturer.getUser().getDob());
        lecturerDTO.setLastAccess(lecturer.getUser().getLastAccess());
        lecturerDTO.setAvatar(lecturer.getUser().getAvatar());

        return lecturerDTO;
    }

}
