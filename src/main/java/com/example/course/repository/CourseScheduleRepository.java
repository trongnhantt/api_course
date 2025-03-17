package com.example.course.repository;

import com.example.course.dto.response.ScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.course.entity.CourseSchedule;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {
    @Query("SELECT new com.example.course.dto.response.ScheduleDTO(" +
            "cs.courseScheduleId, cs.createdDate, cs.updatedDate, cs.startTime, " +
            "cs.endTime, new com.example.course.dto.response.RoomDTO(cs.room.roomId, cs.room.roomName, cs.room.createdDate, cs.room.updatedDate)) " +
            "FROM CourseSchedule cs " +
            "WHERE cs.course.courseId = :courseId ")
    List<ScheduleDTO> findByCourseId(Long courseId);

}
