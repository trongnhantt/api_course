package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
