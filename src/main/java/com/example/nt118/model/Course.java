package com.example.nt118.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@ToString(exclude = {"courseDetail"})
@EqualsAndHashCode(exclude = {"courseDetail"})
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "prerequisites")
    private String prerequisites;

    @Column(name = "department")
    private String department;

    @Column(name = "room")
    private String room;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "semester")
    private String semester;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "current_students")
    private Integer currentStudents;

    @Column(name = "status")
    private String status;

    @Column(name = "teacher_id", insertable = false, updatable = false)
    private String teacherId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    @JsonManagedReference
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
        name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonManagedReference
    private Set<Student> students;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private Set<Grade> grades;

    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private Set<CourseGrade> courseGrades;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private CourseDetail courseDetail;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<AssignmentDeadline> assignmentDeadlines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
} 