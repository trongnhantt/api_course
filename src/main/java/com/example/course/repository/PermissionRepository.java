package com.example.course.repository;

import com.example.course.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository  extends JpaRepository<Permission, Long> {
    boolean existsByPermissionName(String permissionName);
}
