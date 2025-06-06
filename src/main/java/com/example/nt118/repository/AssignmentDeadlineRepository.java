package com.example.nt118.repository;

import com.example.nt118.model.AssignmentDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentDeadlineRepository extends JpaRepository<AssignmentDeadline, Long> {
    int countByStatus(AssignmentDeadline.DeadlineStatus status);

    @Query(value = "SELECT COUNT(*) FROM assignment_deadlines WHERE deadline_date BETWEEN CURRENT_TIMESTAMP AND DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 7 DAY)", nativeQuery = true)
    int countUpcomingWeek();

    @Query("SELECT d FROM AssignmentDeadline d " +
           "WHERE d.status = 'PENDING' " +
           "ORDER BY d.deadlineDate ASC")
    List<AssignmentDeadline> findPendingDeadlinesOrderByDate();

    @Query("SELECT d FROM AssignmentDeadline d " +
           "WHERE d.status = 'COMPLETED' " +
           "ORDER BY d.deadlineDate DESC")
    List<AssignmentDeadline> findCompletedDeadlinesOrderByDate();

    @Query("SELECT d FROM AssignmentDeadline d " +
           "WHERE d.status = 'OVERDUE' " +
           "ORDER BY d.deadlineDate DESC")
    List<AssignmentDeadline> findOverdueDeadlinesOrderByDate();

    @Query("SELECT DISTINCT d FROM AssignmentDeadline d " +
           "LEFT JOIN FETCH d.submissions s " +
           "LEFT JOIN FETCH d.attachments " +
           "JOIN FETCH d.course c " +
           "JOIN FETCH c.teacher " +
           "JOIN c.students st " +
           "WHERE st.studentId = :studentId " +
           "ORDER BY d.deadlineDate ASC")
    List<AssignmentDeadline> findByStudentId(@Param("studentId") String studentId);

    @Query("SELECT DISTINCT d FROM AssignmentDeadline d " +
           "LEFT JOIN FETCH d.submissions s " +
           "LEFT JOIN FETCH d.attachments " +
           "JOIN FETCH d.course c " +
           "JOIN FETCH c.teacher " +
           "WHERE c.courseId = :courseId")
    List<AssignmentDeadline> findByCourseId(@Param("courseId") String courseId);
} 