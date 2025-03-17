package com.example.course.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateStudentRequest {
    private String courseId; // Change from String to Long
    private List<Long> students;

    // Constructors
    public UpdateStudentRequest() {
    }

    public UpdateStudentRequest(String courseId, List<Long> studentIds) {
        this.courseId = courseId;
        this.students = studentIds;
    }

    @Override
    public String toString() {
        return "UpdateStudentRequest{" +
                "courseId=" + courseId +
                ", studentIds=" + students +
                '}';
    }

    public List<Long> getStudentIds() {
        return students;
    }
}
