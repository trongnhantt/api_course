package com.example.course.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetUserStudentResponse;
import com.example.course.dto.response.StudentResponse;
import com.example.course.service.StudentService;
import com.example.course.util.ApiMessage;

@RestController
@RequestMapping(value = "/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/get-students")
    public ResponseEntity<AppResponse<List<GetUserStudentResponse>>> getStudentList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        return new ResponseEntity<AppResponse<List<GetUserStudentResponse>>>(
                new AppResponse<List<GetUserStudentResponse>>(HttpStatus.OK.value(),
                        ApiMessage.SUCCESS, studentService.getAllStudents(page, pageSize, sort, sortDir)),
                HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<AppResponse<StudentResponse>> findStudentWithCourses(
            @RequestParam(value = "studentId") Long studentId) {
        return new ResponseEntity<AppResponse<StudentResponse>>(
                new AppResponse<StudentResponse>(HttpStatus.OK.value(), ApiMessage.SUCCESS,
                        studentService.findStudentWithCourses(studentId)),
                HttpStatus.OK);
    }
}
