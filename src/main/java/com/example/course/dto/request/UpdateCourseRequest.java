package com.example.course.dto.request;

import java.time.LocalDate;

public class UpdateCourseRequest {

    private Long subjectId; // ID của Subject
    private LocalDate startDate; // Ngày bắt đầu
    private LocalDate endDate; // Ngày kết thúc

    // Getters và Setters
    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
