package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
