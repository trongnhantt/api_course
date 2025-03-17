package com.example.course.repository;

import com.example.course.dto.response.RoomDTO;
import com.example.course.dto.response.StudentInCreateCourseDTO;
import com.example.course.entity.Room;
import com.example.course.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Override
    List<Room> findAll();
    @Query("SELECT new com.example.course.dto.response.RoomDTO(" +
            "r.roomId, r.roomName, r.createdDate, r.updatedDate) " +
            "FROM Room r ")
    List<RoomDTO> findRooms(Pageable pageable);
}
