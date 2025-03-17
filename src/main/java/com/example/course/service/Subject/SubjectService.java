package com.example.course.service.Subject;

import com.example.course.dto.request.UpdateCourseRequest;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.ErrorResponse;
import com.example.course.entity.Category;
import com.example.course.entity.Course;
import com.example.course.entity.Subject;
import com.example.course.repository.CategoryRepository;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.SubjectRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService implements ISubjectService {
    private final SubjectRepository subjectRepository;

    private final CategoryRepository categoryRepository;

    private final CourseRepository courseRepository;

    @Override
    public AppResponse<Subject> addSubject(Subject subject) {

        if (subject.getCategory() != null && subject.getCategory().getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(subject.getCategory().getCategoryId());
            if (category.isPresent()) {
                subject.setCategory(category.get());
            } else {
                // Return an error response if the category is not found
                ErrorResponse error = new ErrorResponse(400, "Category with given ID not found");
                return new AppResponse<>(400, "Failed to add subject", error);
            }
        }
        Subject savedSubject = subjectRepository.save(subject);
        return new AppResponse<>(201, "Subject added successfully", savedSubject);
    }

    @Override
    public AppResponse<Subject> updateSubject(Subject subject) {
        return null;
    }

    @Override
    public AppResponse<Subject> deleteSubject(Subject subject) {
        return null;
    }

    @Override
    public AppResponse<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return new AppResponse<>(200, "Get list subject successfully", subjects);
    }

    @Transactional
    public Course updateCourse(Long courseId, UpdateCourseRequest updateCourseRequest) {
        // Tìm khóa học theo ID
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            return null; // Không tìm thấy khóa học
        }

        // Cập nhật thông tin khóa học
        if (updateCourseRequest.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(updateCourseRequest.getSubjectId()).orElse(null);
            if (subject != null) {
                course.setSubject(subject);
            }
        }

        if (updateCourseRequest.getStartDate() != null) {
            course.setStartDate(updateCourseRequest.getStartDate());
        }

        if (updateCourseRequest.getEndDate() != null) {
            course.setEndDate(updateCourseRequest.getEndDate());
        }

        // Lưu lại thông tin khóa học đã sửa
        return courseRepository.save(course);
    }
}
