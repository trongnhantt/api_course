package com.example.course.repository;

import com.example.course.dto.response.LecturerInCreateCourseDTO;
import com.example.course.dto.response.SubjectInCreateCourseDTO;
import com.example.course.entity.Subject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT new com.example.course.dto.response.SubjectInCreateCourseDTO(" +
            "s.subjectId, s.subjectName, s.description, s.createdDate, s.updatedDate, s.image) " +
            "FROM Subject s ")
    List<SubjectInCreateCourseDTO> findSubjects(Pageable pageable);

    @Override
    List<Subject> findAll();
}
