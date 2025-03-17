package com.example.course.repository;

import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.entity.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
        @Query("SELECT new com.example.course.dto.response.CourseDTO(" +
                        "c.courseId, c.createdDate,  c.updatedDate, c.startDate, c.endDate, COUNT(s), c.subject) " +
                        "FROM Course c " +
                        "LEFT JOIN c.students s " +
                        "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, c.subject ")
        List<CourseDTO> getCourses(Pageable pageable);

        @Query("SELECT new com.example.course.dto.response.CourseDTO(" +
                        "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
                        "COUNT(cs), c.subject) " +
                        "FROM Course c " +
                        "LEFT JOIN CourseStudent cs ON cs.course = c " +
                        "WHERE cs.student.id = :studentId " +
                        "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, c.subject")

        List<CourseDTO> getCoursesByStudent(Pageable pageable, @Param("studentId") Long studentId);

        @Query("SELECT new com.example.course.dto.response.CourseDTO(" +
                        "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
                        "COUNT(cl), c.subject) " +
                        "FROM Course c " +
                        "LEFT JOIN CourseLecturer cl ON cl.course = c " +
                        "WHERE cl.lecturer.id = :lectureId " +
                        "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, c.subject")

        List<CourseDTO> getCoursesByLecture(Pageable pageable, @Param("lectureId") Long lectureId);

        @Query("SELECT new com.example.course.dto.response.CourseDTO(" +
                        "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, COUNT(s), c.subject) " +
                        "FROM Course c " +
                        "LEFT JOIN c.students s " +
                        "WHERE c.courseId = :courseId " +
                        "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, c.subject")
        Optional<CourseDTO> getCourseById(Long courseId);

        @Query("SELECT new com.example.course.dto.response.CourseCardDTO(" +
        "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
        "COUNT(s), c.subject, c.price, c.thumbnail, " +
        "c.subject.subjectName, c.subject.description) " +
        "FROM Course c " +
        "LEFT JOIN c.students s " +
        "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
        "c.subject, c.price, c.thumbnail, c.subject.subjectName, c.subject.description")
List<CourseCardDTO> getAllCourseCards(Pageable pageable);

@Query("SELECT new com.example.course.dto.response.CourseCardDTO(" +
        "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
        "COUNT(s), c.subject, c.price, c.thumbnail, " +
        "c.subject.subjectName, c.subject.description) " +
        "FROM Course c " +
        "LEFT JOIN c.students s " +
        "WHERE c.courseId IN :courseIdList " + // Thêm điều kiện IN
        "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
        "c.subject, c.price, c.thumbnail, c.subject.subjectName, c.subject.description")
List<CourseCardDTO> getCourseCardsById(@Param("courseIdList") List<Long> courseIdList);


//@Override
//List<Course> findAll();

        @Override
        List<Course> findAll();

        // @Query("SELECT " +
        // "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
        // "c.lecturers, COUNT(s), c.subject " +
        // "FROM Course c " +
        // "LEFT JOIN c.students s " +
        // "JOIN FETCH c.lecturers " +
        // "GROUP BY c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate,
        // c.lecturers, c.subject " +
        // "ORDER BY c.createdDate DESC")
        // List<Object[]> test(Pageable pageable);

        // @Query("SELECT " +
        // "c.courseId, c.createdDate, c.updatedDate, c.startDate, c.endDate, " +
        // " c.subject " +
        // "FROM Course c JOIN FETCH c.lecturers")
        @Query("SELECT c FROM Course c " +
                        "LEFT JOIN FETCH c.lecturers l ")
        List<Object[]> test(Pageable pageable);


        Course findCourseByCourseId(Long courseId);

        // @Query("SELECT c " +
        // "FROM Course c ")
        // List<Course> getCourses(Pageable pageable);
}
