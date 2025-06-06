package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.AcademicReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AcademicReportServiceImpl implements AcademicReportService {

    @Autowired
    private SemesterRepository semesterRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private CourseGradeRepository courseGradeRepository;
    
    @Autowired
    private AcademicProgressRepository academicProgressRepository;

    @Override
    @Transactional
    public AcademicReportResponse getAcademicReport(String semesterId, String studentId) {
        // Get all courses for the student in the semester
        List<Course> courses = courseRepository.findByStudentIdAndSemester(studentId, semesterId);
        
        // Calculate total credits
        int totalCredits = courses.stream()
                .mapToInt(Course::getCredits)
                .sum();
        
        // Get course grades
        List<CourseGrade> grades = courseGradeRepository.findBySemesterIdAndStudentIdWithTeacher(semesterId, studentId);
        
        // Calculate completed and in-progress courses
        long completedCourses = grades.stream()
                .filter(grade -> "completed".equals(grade.getStatus()))
                .count();
        long inProgressCourses = courses.size() - completedCourses;
        
        // Calculate GPA
        double gpa = calculateGPA(grades);
        
        // Update semester data
        updateSemesterData(semesterId, totalCredits, completedCourses, inProgressCourses, gpa);
        
        // Update academic progress
        updateAcademicProgress(studentId, totalCredits);
        
        // Build response
        AcademicReportResponse response = new AcademicReportResponse();
        response.setStatus("success");
        
        AcademicReportResponse.AcademicReportData data = new AcademicReportResponse.AcademicReportData();
        
        // Set semester info
        AcademicReportResponse.SemesterInfo semesterInfo = new AcademicReportResponse.SemesterInfo();
        semesterInfo.setId(semesterId);
        semesterInfo.setName(semesterRepository.findById(semesterId).get().getName());
        
        AcademicReportResponse.SemesterSummary summary = new AcademicReportResponse.SemesterSummary();
        summary.setGpa(gpa);
        summary.setTotalCredits(totalCredits);
        summary.setCompletedCourses((int) completedCourses);
        summary.setInProgressCourses((int) inProgressCourses);
        semesterInfo.setSummary(summary);
        
        // Map courses with grades
        List<AcademicReportResponse.CourseInfo> courseInfos = courses.stream()
                .map(course -> {
                    AcademicReportResponse.CourseInfo courseInfo = new AcademicReportResponse.CourseInfo();
                    courseInfo.setCourseId(course.getCourseId());
                    courseInfo.setCourseName(course.getCourseName());
                    courseInfo.setCredits(course.getCredits());
                    courseInfo.setDescription(course.getDescription());
                    courseInfo.setDepartment(course.getDepartment());
                    courseInfo.setRoom(course.getRoom());
                    courseInfo.setSchedule(course.getSchedule());
                    courseInfo.setSemester(course.getSemester());
                    courseInfo.setStatus(course.getStatus());
                    
                    // Set teacher info
                    if (course.getTeacher() != null) {
                        AcademicReportResponse.TeacherInfo teacherInfo = new AcademicReportResponse.TeacherInfo();
                        teacherInfo.setTeacherId(course.getTeacher().getTeacherId());
                        teacherInfo.setName(course.getTeacher().getName());
                        teacherInfo.setEmail(course.getTeacher().getEmail());
                        teacherInfo.setPhone(course.getTeacher().getPhone());
                        teacherInfo.setDepartment(course.getTeacher().getDepartment());
                        teacherInfo.setPosition(course.getTeacher().getPosition());
                        teacherInfo.setAvatarUrl(course.getTeacher().getAvatarUrl());
                        courseInfo.setTeacher(teacherInfo);
                    }
                    
                    // Set grades
                    grades.stream()
                            .filter(grade -> grade.getCourse().getCourseId().equals(course.getCourseId()))
                            .findFirst()
                            .ifPresent(grade -> {
                                AcademicReportResponse.GradeInfo gradeInfo = new AcademicReportResponse.GradeInfo();
                                gradeInfo.setMidterm(grade.getMidterm());
                                gradeInfo.setPractice(grade.getPractice());
                                gradeInfo.setFinalGrade(grade.getFinalGrade());
                                gradeInfo.setAverage(grade.getAverage());
                                gradeInfo.setLetterGrade(grade.getLetterGrade());
                                courseInfo.setGrades(gradeInfo);
                            });
                    
                    return courseInfo;
                })
                .collect(Collectors.toList());
        
        // Calculate grade distribution
        Map<String, Integer> gradeDistribution = calculateGradeDistribution(grades);
        
        // Get academic progress
        AcademicProgress academicProgress = academicProgressRepository.findByStudentStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Academic progress not found"));
        
        // Map academic progress
        AcademicReportResponse.AcademicProgressInfo progressInfo = new AcademicReportResponse.AcademicProgressInfo();
        progressInfo.setStudentId(academicProgress.getStudent().getStudentId());
        progressInfo.setTotalRequiredCredits(academicProgress.getTotalRequiredCredits());
        progressInfo.setCompletedCredits(academicProgress.getCompletedCredits());
        progressInfo.setRemainingCredits(academicProgress.getRemainingCredits());
        progressInfo.setEstimatedGraduation(academicProgress.getEstimatedGraduation());
        
        // Set student info
        AcademicReportResponse.StudentInfo studentInfo = new AcademicReportResponse.StudentInfo();
        studentInfo.setStudentId(academicProgress.getStudent().getStudentId());
        studentInfo.setName(academicProgress.getStudent().getName());
        studentInfo.setEmail(academicProgress.getStudent().getEmail());
        studentInfo.setAvatarUrl(academicProgress.getStudent().getAvatarUrl());
        studentInfo.setClassName(academicProgress.getStudent().getClassName());
        studentInfo.setDob(academicProgress.getStudent().getDob().toString());
        progressInfo.setStudent(studentInfo);
        
        // Set all data
        data.setSemester(semesterInfo);
        data.setCourses(courseInfos);
        data.setGradeDistribution(gradeDistribution);
        data.setAcademicProgress(progressInfo);
        
        response.setData(data);
        return response;
    }
    
    private double calculateGPA(List<CourseGrade> grades) {
        if (grades.isEmpty()) return 0.0;
        
        double totalPoints = grades.stream()
                .mapToDouble(grade -> {
                    switch (grade.getLetterGrade()) {
                        case "A": return 4.0;
                        case "B+": return 3.5;
                        case "B": return 3.0;
                        case "C+": return 2.5;
                        case "C": return 2.0;
                        case "D+": return 1.5;
                        case "D": return 1.0;
                        default: return 0.0;
                    }
                })
                .sum();
        
        return Math.round(totalPoints / grades.size() * 100.0) / 100.0;
    }
    
    private Map<String, Integer> calculateGradeDistribution(List<CourseGrade> grades) {
        Map<String, Long> gradeCounts = grades.stream()
                .collect(Collectors.groupingBy(CourseGrade::getLetterGrade, Collectors.counting()));
        
        Map<String, Integer> distribution = Map.of(
            "A", gradeCounts.getOrDefault("A", 0L).intValue(),
            "B+", gradeCounts.getOrDefault("B+", 0L).intValue(),
            "B", gradeCounts.getOrDefault("B", 0L).intValue(),
            "C+", gradeCounts.getOrDefault("C+", 0L).intValue(),
            "C", gradeCounts.getOrDefault("C", 0L).intValue(),
            "D+", gradeCounts.getOrDefault("D+", 0L).intValue(),
            "D", gradeCounts.getOrDefault("D", 0L).intValue(),
            "F", gradeCounts.getOrDefault("F", 0L).intValue()
        );
        
        return distribution;
    }
    
    @Transactional
    private void updateSemesterData(String semesterId, int totalCredits, long completedCourses, 
                                  long inProgressCourses, double gpa) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        
        semester.setTotalCredits(totalCredits);
        semester.setCompletedCourses((int) completedCourses);
        semester.setInProgressCourses((int) inProgressCourses);
        semester.setGpa(gpa);
        
        semesterRepository.save(semester);
    }
    
    @Transactional
    private void updateAcademicProgress(String studentId, int currentSemesterCredits) {
        AcademicProgress progress = academicProgressRepository.findByStudentStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Academic progress not found"));
        
        progress.setCompletedCredits(currentSemesterCredits);
        progress.setRemainingCredits(progress.getTotalRequiredCredits() - currentSemesterCredits);
        
        academicProgressRepository.save(progress);
    }
} 