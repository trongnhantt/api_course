package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
