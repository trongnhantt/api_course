package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import com.example.nt118.service.TuitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TuitionServiceImpl implements TuitionService {

    private static final Logger logger = LoggerFactory.getLogger(TuitionServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TuitionPaymentRepository tuitionPaymentRepository;

    @Override
    public TuitionResponse getStudentTuition(String studentId, String semester) {
        logger.info("getStudentTuition called with studentId: {}, semester: {}", studentId, semester);
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        logger.info("Found student: {}", student);

        List<Course> courses = courseRepository.findByStudentIdAndSemester(studentId, semester);
        logger.info("Found courses: {}", courses);

        TuitionResponse response = new TuitionResponse();
        response.setSemester(semester);

        List<TuitionResponse.CourseTuition> courseTuitions = courses.stream()
                .map(course -> {
                    logger.info("Processing course: {}", course);
                    TuitionResponse.CourseTuition courseTuition = new TuitionResponse.CourseTuition();
                    courseTuition.setClassId(course.getCourseId());
                    courseTuition.setCredits(course.getCredits());
                    courseTuition.setAmount(course.getCredits() * 1000000L); // 1 credit = 1,000,000 VND
                    courseTuition.setSubjectName(course.getCourseName());

                    List<TuitionPayment> payments = tuitionPaymentRepository.findByStudentAndCourse(student, course);
                    TuitionPayment payment = payments.isEmpty() ? null : payments.get(0);
                    logger.info("Found payment for course {}: {}", course.getCourseId(), payment);

                    if (payment != null) {
                        courseTuition.setStatus(payment.getStatus());
                        courseTuition.setPaymentDate(payment.getPaymentDate());
                        courseTuition.setPaymentMethod(payment.getPaymentMethod());
                    } else {
                        courseTuition.setStatus(TuitionResponse.PaymentStatus.UNPAID);
                        courseTuition.setPaymentDate(null);
                        courseTuition.setPaymentMethod(null);
                    }

                    return courseTuition;
                })
                .collect(Collectors.toList());

        response.setCourses(courseTuitions);

        long totalAmount = courseTuitions.stream()
                .mapToLong(TuitionResponse.CourseTuition::getAmount)
                .sum();
        int totalCredits = courseTuitions.stream()
                .mapToInt(TuitionResponse.CourseTuition::getCredits)
                .sum();

        response.setTotalAmount(totalAmount);
        response.setTotalCredits(totalCredits);

        TuitionResponse.PaymentSummary summary = new TuitionResponse.PaymentSummary();
        summary.setPaid(courseTuitions.stream()
                .filter(c -> c.getStatus() == TuitionResponse.PaymentStatus.PAID)
                .mapToLong(TuitionResponse.CourseTuition::getAmount)
                .sum());
        summary.setUnpaid(courseTuitions.stream()
                .filter(c -> c.getStatus() == TuitionResponse.PaymentStatus.UNPAID)
                .mapToLong(TuitionResponse.CourseTuition::getAmount)
                .sum());
        summary.setPartial(courseTuitions.stream()
                .filter(c -> c.getStatus() == TuitionResponse.PaymentStatus.PARTIAL)
                .mapToLong(TuitionResponse.CourseTuition::getAmount)
                .sum());

        response.setPaymentSummary(summary);
        logger.info("Returning response: {}", response);
        return response;
    }
} 