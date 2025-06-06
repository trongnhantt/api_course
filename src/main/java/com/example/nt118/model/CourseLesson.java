package com.example.nt118.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_lessons")
public class CourseLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id")
    private String courseId;

    private String title;
    private String duration;
    private String videoUrl;

    @ElementCollection
    @CollectionTable(name = "lesson_materials", joinColumns = @JoinColumn(name = "lesson_id"))
    @Column(name = "material")
    private List<String> materials;
} 