package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.FileFolder;
import com.example.course.entity.composite.FileFolderId;

public interface FileFolderRepository extends JpaRepository<FileFolder, FileFolderId> {
}
