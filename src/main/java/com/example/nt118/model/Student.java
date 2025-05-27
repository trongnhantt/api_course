package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "students")
public class Student {
      @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "class_name")
    private String className;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    // Quan hệ nhiều-nhiều với Course
    @ManyToMany
    @JoinTable(
        name = "course_student",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
} 