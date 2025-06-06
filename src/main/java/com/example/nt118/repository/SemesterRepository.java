package com.example.nt118.repository;

import com.example.nt118.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
} 