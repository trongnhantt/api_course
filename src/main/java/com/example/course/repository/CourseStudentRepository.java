package com.example.course.repository;

import com.example.course.dto.response.StudentInCreateCourseDTO;
import com.example.course.dto.response.StudentNotInCourseDTO;
import com.example.course.entity.CourseStudent;
import com.example.course.entity.composite.CourseStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentId> {
//    List<CourseStudent> getCourseStudentListByCourseId();

    @Query("SELECT new com.example.course.dto.response.StudentInCreateCourseDTO(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM CourseStudent cs " +
            "LEFT JOIN cs.student s " +
            "LEFT JOIN s.user u WHERE cs.course.courseId = :courseId")
    List<StudentInCreateCourseDTO> findByCourseId(Long courseId, Pageable pageable);

    @Query("SELECT COUNT(*) " +
            "FROM CourseStudent cs " +
            "WHERE cs.id.courseId = :courseId")
    Integer countStudentsByCourseId(Long courseId);

    @Query("SELECT COUNT(s.studentId) " +
            "FROM Student s " +
            "WHERE s.studentId NOT IN (" +
            "SELECT cs.student.studentId FROM CourseStudent cs WHERE cs.course.courseId = :courseId)")
    Integer countStudentsNotInCourse(Long courseId);
    @Query("SELECT new com.example.course.dto.response.StudentNotInCourseDTO(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM Student s " +
            "LEFT JOIN s.user u " +
            "WHERE s.studentId NOT IN (" +
            "SELECT cs.student.studentId FROM CourseStudent cs WHERE cs.course.courseId = :courseId)" )
    List<StudentNotInCourseDTO> findStudentsNotInCourse(Long courseId, Pageable pageable);

    @Query("SELECT new com.example.course.dto.response.StudentInCreateCourseDTO(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM CourseStudent cs " +
            "LEFT JOIN cs.student s " +
            "LEFT JOIN s.user u WHERE cs.course.courseId = :courseId")
    List<StudentInCreateCourseDTO> findByStudentCourseId(Long courseId);
}
