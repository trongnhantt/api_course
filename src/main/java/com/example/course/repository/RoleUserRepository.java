package com.example.course.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.course.entity.Role;
import com.example.course.entity.RoleUser;
import com.example.course.entity.User;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    boolean existsByUserAndRole(User user, Role role);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM role_user WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
}
