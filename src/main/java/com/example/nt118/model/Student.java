package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "student_id")
    private String studentId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "dob")
    private String dob;
    
    @Column(name = "avatar_url")
    private String avatarUrl;
    
    @Column(name = "class_name")
    private String className;
    
    @Column(name = "password")
    private String password;
} 