package com.example.nt118.repository;

import com.example.nt118.model.CourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseGradeRepository extends JpaRepository<CourseGrade, Long> {
    @Query("SELECT DISTINCT cg FROM CourseGrade cg " +
           "LEFT JOIN FETCH cg.course c " +
           "LEFT JOIN FETCH c.teacher " +
           "WHERE cg.semester.id = :semesterId " +
           "AND cg.student.studentId = :studentId")
    List<CourseGrade> findBySemesterIdAndStudentIdWithTeacher(
        @Param("semesterId") String semesterId,
        @Param("studentId") String studentId
    );

    @Query("SELECT cg FROM CourseGrade cg " +
           "WHERE cg.course.courseId = :courseId " +
           "AND cg.student.studentId = :studentId")
    Optional<CourseGrade> findByCourseIdAndStudentId(
        @Param("courseId") String courseId,
        @Param("studentId") String studentId
    );

    @Query("SELECT cg FROM CourseGrade cg WHERE cg.course.courseId = :courseId")
    List<CourseGrade> findByCourseId(@Param("courseId") String courseId);
} 