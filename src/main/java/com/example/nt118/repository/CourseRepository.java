package com.example.nt118.repository;

import com.example.nt118.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.studentId = :studentId")
    List<Course> findByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.studentId = :studentId AND c.status = :status")
    List<Course> findByStudentIdAndStatus(@Param("studentId") String studentId, @Param("status") String status);

    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.studentId = :studentId AND c.semester = :semester")
    List<Course> findByStudentIdAndSemester(@Param("studentId") String studentId, @Param("semester") String semester);
} 