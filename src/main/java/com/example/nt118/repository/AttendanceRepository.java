package com.example.nt118.repository;

import com.example.nt118.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT DISTINCT a FROM Attendance a " +
           "LEFT JOIN FETCH a.course c " +
           "LEFT JOIN FETCH c.teacher " +
           "WHERE a.student.studentId = :studentId " +
           "ORDER BY a.sessionDate DESC")
    List<Attendance> findByStudentId(@Param("studentId") String studentId);

    @Query("SELECT DISTINCT a FROM Attendance a " +
           "LEFT JOIN FETCH a.course c " +
           "LEFT JOIN FETCH c.teacher " +
           "WHERE a.student.studentId = :studentId " +
           "AND a.course.courseId = :courseId " +
           "ORDER BY a.sessionDate DESC")
    List<Attendance> findByStudentIdAndCourseId(
        @Param("studentId") String studentId,
        @Param("courseId") String courseId
    );
} 