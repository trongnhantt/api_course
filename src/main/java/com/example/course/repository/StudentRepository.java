package com.example.course.repository;

import com.example.course.dto.response.GetUserStudentResponse;
import com.example.course.dto.response.LecturerInCreateCourseDTO;
import com.example.course.dto.response.StudentInCreateCourseDTO;
import com.example.course.dto.response.StudentResponse;
import com.example.course.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Override
    List<Student> findAll();

    @Query("SELECT new com.example.course.dto.response.StudentInCreateCourseDTO(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess, u.avatar) " +
            "FROM Student s " +
            "LEFT JOIN s.user u ")
    List<StudentInCreateCourseDTO> findStudents(Pageable pageable);

    @Query("SELECT new com.example.course.dto.response.GetUserStudentResponse(" +
            "s.studentId, u.userId, u.username, u.email, u.gender, u.dob) " +
            "FROM Student s " +
            "LEFT JOIN s.user u ")
    List<GetUserStudentResponse> getUserStudents(Pageable pageable);

    @Query("SELECT s.studentId, u.userId, u.username, u.email, u.gender, u.dob, " +
            "c.courseId, c.startDate, c.endDate " +
            "FROM Student s " +
            "LEFT JOIN s.user u " +
            "LEFT JOIN s.courses cs " +
            "LEFT JOIN cs.course c " +
            "WHERE s.studentId = :studentId")
    List<Object[]> findStudentWithCourses(@Param("studentId") Long studentId);

    // @Query("SELECT new com.example.course.dto.response.StudentInCreateCourseDTO("
    // +
    // "s.studentId, u.userId, u.username, u.email, u.gender, u.dob, u.lastAccess,
    // u.avatar) " +
    // "FROM Student s " +
    // "LEFT JOIN s.user u " +
    // "LEFT JOIN CourseStudent cs " +
    // "WHERE cs.course.courseId = :courseId")
    // List<StudentInCreateCourseDTO> findStudentsByCourseId(Long courseId, Pageable
    // pageable);
    //
    // @Query("SELECT COUNT(*) " +
    // "FROM Student s " +
    // "LEFT JOIN s.user u " +
    // "LEFT JOIN CourseStudent cs " +
    // "WHERE cs.course.courseId = :courseId")
    // Integer countStudentsByCourseId(Long courseId);
}
