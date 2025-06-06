package com.example.nt118.controller;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Course Management", description = "APIs for managing student courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseLessonRepository courseLessonRepository;

    @Autowired
    private CourseNotificationRepository courseNotificationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseGradeRepository courseGradeRepository;
    
    @Operation(
        summary = "Get all courses for a student",
        description = "Retrieves a list of all courses associated with a specific student ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved courses",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Course.class))),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/courses/{studentId}")
    public ResponseEntity<List<Course>> getCoursesByStudentId(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId) {
        return ResponseEntity.ok(courseService.getCoursesByStudentId(studentId));
    }
    
    @Operation(
        summary = "Get courses by status for a student",
        description = "Retrieves a list of courses with a specific status for a student"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved courses"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/courses/{studentId}/status/{status}")
    public ResponseEntity<List<Course>> getStudentCoursesByStatus(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId,
            @Parameter(description = "Status of the courses (e.g., Active, Completed)", required = true)
            @PathVariable String status) {
        return ResponseEntity.ok(courseService.getStudentCoursesByStatus(studentId, status));
    }
    
    @Operation(
        summary = "Get courses by semester for a student",
        description = "Retrieves a list of courses for a specific semester"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved courses"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/courses/{studentId}/semester/{semester}")
    public ResponseEntity<List<Course>> getStudentCoursesBySemester(
            @Parameter(description = "ID of the student", required = true)
            @PathVariable String studentId,
            @Parameter(description = "Semester (e.g., 2024-1)", required = true)
            @PathVariable String semester) {
        return ResponseEntity.ok(courseService.getStudentCoursesBySemester(studentId, semester));
    }
    
    @Operation(
        summary = "Get course by ID",
        description = "Retrieves detailed information about a specific course"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved course"),
        @ApiResponse(responseCode = "404", description = "Course not found")
    })
    @GetMapping("/courses/{studentId}/{courseId}")
    public ResponseEntity<?> getCourseDetailForStudent(
            @PathVariable String studentId,
            @PathVariable String courseId) {
        
        Optional<Student> studentOpt = studentRepository.findStudentByCourseIdAndStudentId(studentId, courseId);
        
        if (!studentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!courseOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOpt.get();
        CourseDetailResponse response = new CourseDetailResponse();
        response.setStatus("success");

        CourseDetailResponse.CourseData data = new CourseDetailResponse.CourseData();
        
        // Set course info
        CourseDetailResponse.CourseInfo courseInfo = new CourseDetailResponse.CourseInfo();
        courseInfo.setId(course.getCourseId());
        courseInfo.setName(course.getCourseName());
        courseInfo.setInstructor(course.getTeacher().getName());
        courseInfo.setCredit(course.getCredits());
        courseInfo.setRoom(course.getRoom());
        courseInfo.setSchedule(course.getSchedule());
        courseInfo.setDescription(course.getDescription());
        courseInfo.setDepartment(course.getDepartment());
        courseInfo.setSemester(course.getSemester());
        courseInfo.setStatus(course.getStatus());
        data.setCourseInfo(courseInfo);

        // Set schedule
        CourseDetailResponse.Schedule schedule = new CourseDetailResponse.Schedule();
        schedule.setDayOfWeek(course.getSchedule().split(" ")[0]);
        String[] timeRange = course.getSchedule().split(" ")[1].split("-");
        schedule.setStartTime(timeRange[0]);
        schedule.setEndTime(timeRange[1]);
        schedule.setRoom(course.getRoom());
        data.setSchedule(schedule);

        // Set grades
        Optional<CourseGrade> courseGradeOpt = courseGradeRepository.findByCourseIdAndStudentId(courseId, studentId);
        if (courseGradeOpt.isPresent()) {
            CourseGrade grade = courseGradeOpt.get();
            CourseDetailResponse.Grades grades = new CourseDetailResponse.Grades();
            grades.setMidterm(grade.getMidterm());
            grades.setPractice(grade.getPractice());
            grades.setFinalGrade(grade.getFinalGrade());
            grades.setAverage(grade.getAverage());
            grades.setLetterGrade(grade.getLetterGrade());
            data.setGrades(grades);
        }

        // Set attendance (placeholder for now)
        CourseDetailResponse.Attendance attendance = new CourseDetailResponse.Attendance();
        attendance.setTotalSessions(0);
        attendance.setAttendedSessions(0);
        attendance.setAbsentSessions(0);
        attendance.setAttendanceRate(0.0);
        data.setAttendance(attendance);

        response.setData(data);
        return ResponseEntity.ok(response);
    }
} 