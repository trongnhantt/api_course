package com.example.nt118.model;

import lombok.Data;

@Data
public class StudentProfileResponse {
    private String studentId;
    private String name;
    private String email;
    private String dob;
    private String avatarUrl;
    private String className;
    private String role;
    private String message;
    private boolean status;
} 