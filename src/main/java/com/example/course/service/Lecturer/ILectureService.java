package com.example.course.service.Lecturer;

import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.GetLecturerDTO;
import com.example.course.dto.response.LecturerDTO;
import com.example.course.dto.response.LecturerUserDTO;
import com.example.course.entity.Lecturer;

import java.util.List;

public interface ILectureService {


    AppResponse<LecturerUserDTO> addLecturer(Lecturer lecturer);
    GetLecturerDTO getAllLecture(Integer page, Integer pageSize, String sort, String sortDir);
    AppResponse<Lecturer> updateLecture(Lecturer lecture);
    AppResponse<Lecturer> deleteLecture(Lecturer lecture);

}
