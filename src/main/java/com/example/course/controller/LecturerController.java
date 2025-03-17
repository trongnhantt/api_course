package com.example.course.controller;

import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.GetLecturerDTO;
import com.example.course.dto.response.LecturerDTO;
import com.example.course.dto.response.LecturerUserDTO;
import com.example.course.dto.response.StudentResponse;
import com.example.course.entity.Lecturer;
import com.example.course.service.Lecturer.LectureService;
import com.example.course.service.StudentService;
import com.example.course.util.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LecturerController {

    @Autowired
    private final LectureService lectureService;

    @Autowired
    private final StudentService studentService;

    @GetMapping("/get-lectures")
    public ResponseEntity<AppResponse<GetLecturerDTO>> getLecturers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sort", defaultValue = "1") String sort,
            @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
        GetLecturerDTO lecturers = lectureService.getAllLecture(page, pageSize, sort, sortDir);
        return ResponseEntity.ok(
                new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, lecturers));
    }

    /**
     * API to delete a lecturer by ID.
     */
    @DeleteMapping("/delete-lecturer/{lecturerId}")
    public ResponseEntity<AppResponse<Void>> deleteLecturer(@PathVariable Long lecturerId) {
        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerId(lecturerId);
        AppResponse<Lecturer> response = lectureService.deleteLecture(lecturer);

        if ("Lecturer deleted successfully".equals(response.getMessage())) {
            return ResponseEntity.ok(
                    new AppResponse<>(HttpStatus.OK.value(), response.getMessage(), null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new AppResponse<>(HttpStatus.BAD_REQUEST.value(), response.getMessage(), null));
        }
    }

    /**
     * API to add a new lecturer.
     */
    @PostMapping("/addLecturers")
    public ResponseEntity<AppResponse<LecturerUserDTO>> addLecturer(@RequestBody Lecturer lecturer) {
        // Gọi service để xử lý logic thêm Lecturer
        AppResponse<LecturerUserDTO> response = lectureService.addLecturer(lecturer);

        // Kiểm tra phản hồi từ service
        if ("Lecturer added successfully".equals(response.getMessage())) {
            // Trả về phản hồi thành công với mã trạng thái 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            // Trả về phản hồi thất bại với mã trạng thái 400 (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<AppResponse<StudentResponse>> findStudentWithCourses(
            @RequestParam(value = "studentId") Long studentId) {
        return new ResponseEntity<AppResponse<StudentResponse>>(
                new AppResponse<StudentResponse>(HttpStatus.OK.value(), ApiMessage.SUCCESS,
                        studentService.findStudentWithCourses(studentId)),
                HttpStatus.OK);
    }

}