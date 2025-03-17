package com.example.course.mapper;

import com.example.course.dto.response.LecturerInCreateCourseDTO;
import com.example.course.entity.Lecturer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LecturerMapper {
//    @Mapping(target = "lecturerId", source = "lecturer.lecturerId")
//    @Mapping(target = "userId", source = "lecturer.user.userId")
//    @Mapping(target = "username", source = "lecturer.user.username")
//    @Mapping(target = "email", source = "lecturer.user.email")
//    @Mapping(target = "gender", source = "lecturer.user.gender")
//    @Mapping(target = "dob", source = "lecturer.user.dob")
//    @Mapping(target = "lastAccess", source = "lecturer.user.lastAccess")
//    @Mapping(target = "avatar", source = "lecturer.user.avatar")
//    public void mapLecturerToLecturerInCreateCourseDTO(@MappingTarget LecturerInCreateCourseDTO lecturerDTO, Lecturer lecturer);
}
