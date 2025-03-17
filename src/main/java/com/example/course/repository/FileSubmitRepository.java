package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.FileSubmit;
import com.example.course.entity.composite.FileSubmitId;

public interface FileSubmitRepository extends JpaRepository<FileSubmit, FileSubmitId> {
}
