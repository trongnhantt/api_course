package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.PermissionUser;

public interface PermissionUserRepository extends JpaRepository<PermissionUser, Long> {
}
