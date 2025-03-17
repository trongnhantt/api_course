package com.example.course.controller;

import com.example.course.dto.SubjectDTO;
import com.example.course.dto.response.AppResponse;
import com.example.course.entity.Category;
import com.example.course.entity.Subject;
import com.example.course.service.Subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
    @PostMapping
    public ResponseEntity<?> addSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setDescription(subjectDTO.getDescription());
        if (subjectDTO.getCategoryId() != null) {
            subject.setCategory(new Category(subjectDTO.getCategoryId()));
        }
        return ResponseEntity.ok(subjectService.addSubject(subject));
    }
}
