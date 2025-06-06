package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@ToString(exclude = {"course"})
@EqualsAndHashCode(exclude = {"course"})
@Entity
@Table(name = "course_details")
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "theory_credits")
    private Integer theoryCredits;

    @Column(name = "practice_credits")
    private Integer practiceCredits;

    @Column(name = "self_study_hours")
    private Integer selfStudyHours;

    @Column(name = "class_hours_per_week")
    private Integer classHoursPerWeek;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "classroom_location")
    private String classroomLocation;

    @Column(name = "syllabus_url")
    private String syllabusUrl;

    @Column(name = "course_materials_url")
    private String courseMaterialsUrl;

    @Column(name = "additional_requirements")
    private String additionalRequirements;
} 