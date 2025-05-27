package com.example.nt118.service;

import com.example.nt118.dto.StudentGradeDTO;
import com.example.nt118.repository.StudentGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentGradeService {
    @Autowired
    private StudentGradeRepository studentGradeRepository;

    public List<StudentGradeDTO> getStudentGrades(String studentId) {
        return studentGradeRepository.findStudentGradesByStudentId(studentId);
    }
} 