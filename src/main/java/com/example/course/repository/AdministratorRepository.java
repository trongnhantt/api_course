package com.example.course.repository;

import com.example.course.entity.Administrator;
import com.example.course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
//    Administrator findByAdministratorId(Long administratorId);
}
