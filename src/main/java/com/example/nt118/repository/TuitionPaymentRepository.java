package com.example.nt118.repository;

import com.example.nt118.model.TuitionPayment;
import com.example.nt118.model.Student;
import com.example.nt118.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TuitionPaymentRepository extends JpaRepository<TuitionPayment, Long> {
    List<TuitionPayment> findByStudentAndCourse(Student student, Course course);
} 