package com.example.course.dto.response;

import java.time.LocalDateTime;

public class LecturerUserDTO {

    private Long lecturerId;
    private String username;
    private String email;
    private Boolean gender;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    public LecturerUserDTO(Long lecturerId, String username, String email, Boolean gender, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.lecturerId = lecturerId;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getter và Setter cho các trường
    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
