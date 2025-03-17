package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
