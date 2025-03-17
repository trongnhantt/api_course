package com.example.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.course.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
