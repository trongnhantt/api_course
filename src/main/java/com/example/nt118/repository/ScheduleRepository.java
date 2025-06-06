package com.example.nt118.repository;

import com.example.nt118.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s " +
           "JOIN s.course c " +
           "JOIN c.students st " +
           "WHERE st.studentId = :studentId " +
           "AND s.dayOfWeek = :dayOfWeek")
    List<Schedule> findByStudentIdAndDayOfWeek(
        @Param("studentId") String studentId,
        @Param("dayOfWeek") String dayOfWeek
    );
} 