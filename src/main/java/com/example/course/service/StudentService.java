package com.example.course.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.course.dto.response.GetUserStudentResponse;
import com.example.course.dto.response.StudentCourseResponse;
import com.example.course.dto.response.StudentResponse;
import com.example.course.entity.Student;
import com.example.course.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    public List<GetUserStudentResponse> getAllStudents(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeStudents(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<GetUserStudentResponse> students = studentRepository.getUserStudents(pageable);

        return students;
    }

    private String getSortAttributeStudents(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "s.studentId");
        sortMapper.put(2, "u.username");
        return sortMapper.get(Integer.valueOf(sort));
    }

    // public StudentResponse findStudentWithCourses(Long studentId) {
    // return studentRepository.findStudentWithCourses(studentId);
    // }

    public StudentResponse findStudentWithCourses(Long studentId) {
        List<Object[]> results = studentRepository.findStudentWithCourses(studentId);

        if (results.isEmpty()) {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }

        // Extract student details (assumes student details are identical in each row)
        Object[] firstRow = results.get(0);
        StudentResponse studentResponse = new StudentResponse(
                (Long) firstRow[0], // studentId
                (Long) firstRow[1], // userId
                (String) firstRow[2], // username
                (String) firstRow[3], // email
                (Boolean) firstRow[4], // gender
                (LocalDate) firstRow[5], // dob
                null // Placeholder for courses
        );

        // Map courses
        List<StudentCourseResponse> courses = results.stream()
                .map(row -> new StudentCourseResponse(
                        (Long) row[6], // courseId
                        (LocalDate) row[7], // startDate
                        (LocalDate) row[8] // endDate
                ))
                .toList();

        studentResponse.setStudentCourse(courses);
        return studentResponse;
    }

    public String test() {
        return "j cung đc";
    }

    // public Student updateStudent(Long studentId, Student studentDetails) {
    // return studentRepository.findById(studentId).map(student -> {
    // student.setName(studentDetails.getName());
    // student.setGender(studentDetails.getGender());
    // student.setDob(studentDetails.getDob());
    // student.setEmail(studentDetails.getEmail());
    // return studentRepository.save(student);
    // }).orElseThrow(() -> new RuntimeException("Student not found with ID: " +
    // studentId));
    // }

    public void deleteAStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }
}
