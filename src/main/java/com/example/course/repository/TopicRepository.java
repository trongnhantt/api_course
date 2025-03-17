package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
