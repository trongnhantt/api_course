package com.example.nt118.Model;

public class LoginRequest {
    private String studentId;
    private String password;

    public LoginRequest(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }

    // Getters and setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 