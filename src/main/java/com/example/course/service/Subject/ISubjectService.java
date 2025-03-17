package com.example.course.service.Subject;

import com.example.course.dto.response.AppResponse;
import com.example.course.entity.Subject;

import java.util.List;

public interface ISubjectService {
    AppResponse<Subject> addSubject(Subject subject);
    AppResponse<Subject> updateSubject(Subject subject);
    AppResponse<Subject> deleteSubject(Subject subject);
    AppResponse<List<Subject>> getAllSubjects();
}
