package com.example.nt118.service;

import com.example.nt118.model.StudentProfileResponse;
import com.example.nt118.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentProfileResponse getStudentProfile(String studentId) {
        StudentProfileResponse response = new StudentProfileResponse();
        
        try {
            return studentRepository.findByStudentId(studentId)
                .map(student -> {
                    response.setStatus(true);
                    response.setMessage("Lấy thông tin sinh viên thành công");
                    response.setStudentId(student.getStudentId());
                    response.setName(student.getName());
                    response.setEmail(student.getEmail());
                    response.setDob(student.getDob());
                    response.setAvatarUrl(student.getAvatarUrl());
                    response.setClassName(student.getClassName());
                    response.setRole("student");
                    return response;
                })
                .orElseGet(() -> {
                    response.setStatus(false);
                    response.setMessage("Không tìm thấy sinh viên với mã: " + studentId);
                    return response;
                });
        } catch (Exception e) {
            response.setStatus(false);
            response.setMessage("Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
            return response;
        }
    }
} 