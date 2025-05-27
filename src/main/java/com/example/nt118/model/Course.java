package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "credits")
    private int credits;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "room")
    private String room;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "semester")
    private String semester;

    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "status")
    private String status;

    // Quan hệ nhiều-nhiều với Student
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
} 