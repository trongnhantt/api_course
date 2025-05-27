package com.example.nt118.repository;

import com.example.nt118.dto.StudentGradeDTO;
import com.example.nt118.model.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {
    @Query("SELECT new com.example.nt118.dto.StudentGradeDTO(c.courseName, c.instructor, c.credits, c.courseId, sg.finalGrade) " +
           "FROM StudentGrade sg " +
           "JOIN Course c ON sg.courseId = c.courseId " +
           "WHERE sg.studentId = :studentId")
    List<StudentGradeDTO> findStudentGradesByStudentId(@Param("studentId") String studentId);
} 