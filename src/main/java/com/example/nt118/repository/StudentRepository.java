package com.example.nt118.repository;

import com.example.nt118.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByStudentIdAndPassword(String studentId, String password);
    
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.courseId = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") String courseId);
    
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE s.studentId = :studentId AND c.courseId = :courseId")
    Optional<Student> findStudentByCourseIdAndStudentId(@Param("studentId") String studentId, @Param("courseId") String courseId);
} 