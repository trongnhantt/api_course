package com.example.course.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubjectInCreateCourseDTO {
    private Long subjectId;
    private String subjectName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
    private byte[] image;

    public SubjectInCreateCourseDTO(Long subjectId, String subjectName, String description, LocalDateTime createdDate, LocalDateTime updatedDate, byte[] image) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.image = image;
    }
}
