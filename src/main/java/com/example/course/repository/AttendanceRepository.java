package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Attendance;
import com.example.course.entity.composite.AttendanceId;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
}
