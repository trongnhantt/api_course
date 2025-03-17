package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.CourseRoomMessage;

public interface CourseRoomMessageRepository extends JpaRepository<CourseRoomMessage, Long> {
}
