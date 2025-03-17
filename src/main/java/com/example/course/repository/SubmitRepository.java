package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entity.Submit;

public interface SubmitRepository extends JpaRepository<Submit, Long> {
}
