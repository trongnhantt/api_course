package com.example.course.controller;

import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.response.*;
import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCoursesDTO;
import com.example.course.dto.request.UpdateStudentRequest;
import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCoursesDTO;
import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.response.*;
import com.example.course.entity.HistoryView;
import com.example.course.exception.AppRuntimeException;
import com.example.course.service.CourseService;
import com.example.course.util.ApiMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.course.util.constant.ExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.request.UpdateCourseRequest;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.CreateCourseResponse;
import com.example.course.dto.response.GetLecturersDTO;
import com.example.course.dto.response.GetRoomsDTO;
import com.example.course.dto.response.GetStudentsDTO;
import com.example.course.dto.response.GetSubjectsDTO;
import com.example.course.entity.Course;
import com.example.course.entity.User;
import com.example.course.repository.UserRepository;
import com.example.course.service.CourseService;
import com.example.course.util.ApiMessage;
import org.springframework.web.client.RestTemplate;

@RestController
public class CourseController {
        @Autowired
        private CourseService courseService;

        @Autowired
        private UserRepository userRepository;

        @GetMapping("/get-course-list")
        public ResponseEntity<AppResponse<GetCoursesDTO>> getCourses(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "userId", defaultValue = "0") Long userId,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                System.out.println(page);
                User user = userRepository.findById(userId).get();
                if (user.getLecturer() != null) {
                        System.out.println(
                                        user.getLecturer().getLecturerId());
                        return new ResponseEntity<AppResponse<GetCoursesDTO>>(
                                        new AppResponse<GetCoursesDTO>(HttpStatus.OK.value(),
                                                        ApiMessage.SUCCESS,
                                                        courseService.getCoursesByLecturer(page, pageSize, sort,
                                                                        sortDir,
                                                                        user.getLecturer().getLecturerId())),
                                        HttpStatus.OK);
                }
                if (user.getStudent() != null) {
                        return new ResponseEntity<AppResponse<GetCoursesDTO>>(
                                        new AppResponse<GetCoursesDTO>(HttpStatus.OK.value(),
                                                        ApiMessage.SUCCESS,
                                                        courseService.getCoursesByStudent(page, pageSize, sort, sortDir,
                                                                        user.getStudent().getStudentId())),
                                        HttpStatus.OK);
                }

                return new ResponseEntity<AppResponse<GetCoursesDTO>>(
                                new AppResponse<GetCoursesDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getCourses(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-lecturer-list")
        public ResponseEntity<AppResponse<GetLecturersDTO>> getLecturersList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetLecturersDTO>>(
                                new AppResponse<GetLecturersDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getLecturers(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/cards")
        public ResponseEntity<AppResponse<GetCourseCardDTO>> getAllCourseCards(
                @RequestParam(value = "page", defaultValue = "1") int page,
                @RequestParam(value = "pageSize", defaultValue = "12") int pageSize,
                @RequestParam(value = "sort", defaultValue = "1") String sort,
                @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir
        ) {
            return new ResponseEntity<AppResponse<GetCourseCardDTO>>(
                    new AppResponse<GetCourseCardDTO>(HttpStatus.OK.value(),
                            ApiMessage.SUCCESS,
                            courseService.getAllCourseCards(page, pageSize, sort, sortDir)),
                    HttpStatus.OK);
        }

        @GetMapping("/get-subject-list")
        public ResponseEntity<AppResponse<GetSubjectsDTO>> getSubjectList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetSubjectsDTO>>(
                                new AppResponse<GetSubjectsDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getSubjects(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/history-view")
        public ResponseEntity<AppResponse<Boolean>> historyView(@RequestParam(value = "courseId") Long courseId) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long userId = Long.valueOf(authentication.getName());

            return new ResponseEntity<AppResponse<Boolean>>(
                    new AppResponse<Boolean>(HttpStatus.OK.value(),
                            ApiMessage.SUCCESS,
                            courseService.historyView(userId, courseId)),
                    HttpStatus.OK);

        }
    @GetMapping("/get-recommendation")
    public ResponseEntity<AppResponse<List<CourseCardDTO>>> getSubjectList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());
        List<HistoryView> historyViewList = courseService.getHistoryView(userId);
        List<Long> input = historyViewList.stream().map((item) -> item.getCourse().getCourseId()).toList();

        System.out.println("sizeee: " +  input.size());
        String flaskApiUrl = "https://coursesrecommendation-ddebe9gadebvgncp.southeastasia-01.azurewebsites.net";
        String flaskApiKey = "your_secret_api_key";


        String url = flaskApiUrl + "/predict?apiKey=" + flaskApiKey;

        // Tạo RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Tạo dữ liệu gửi (mảng 5 phần tử)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("data", input);

        // Tạo HTTP request với Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Gửi HTTP POST đến Flask API
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            System.out.println("111111");

            // Kiểm tra phản hồi
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                // Lấy kết quả dự đoán
                System.out.println("2222");
                Map<String, Object> responseBody = responseEntity.getBody();
                List<Long> prediction = (List<Long>)responseBody.get("prediction");

                // Trả về kết quả với trạng thái OK
                return new ResponseEntity<AppResponse<List<CourseCardDTO>>>(
                        new AppResponse<List<CourseCardDTO>>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS,
                                courseService.getCourseCardsById(prediction)),
                        HttpStatus.OK);
            } else {
                throw new AppRuntimeException(ExceptionType.INTERNAL_SERVER_ERROR, "Recommendation Server Error2");
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi
            throw new AppRuntimeException(ExceptionType.INTERNAL_SERVER_ERROR, "Recommendation Server Error " + e.getMessage());
        }

    }

    @GetMapping("/get-student-list")
        public ResponseEntity<AppResponse<GetStudentsDTO>> getStudentList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetStudentsDTO>>(
                                new AppResponse<GetStudentsDTO>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS,
                                                courseService.getStudents(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-room-list")
        public ResponseEntity<AppResponse<GetRoomsDTO>> getRoomList(
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetRoomsDTO>>(new AppResponse<GetRoomsDTO>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, courseService.getRooms(page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-course-by-id")
        public ResponseEntity<AppResponse<CourseDTO>> getCourseById(
                        @RequestParam(value = "courseId") Long courseId) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<CourseDTO>>(new AppResponse<CourseDTO>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, courseService.getCourseById(courseId)), HttpStatus.OK);
        }

        @GetMapping("/get-students-by-course-id")
        public ResponseEntity<AppResponse<GetStudentsDTO>> getStudentsByCourseId(
                        @RequestParam(value = "courseId") Long courseId,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<GetStudentsDTO>>(new AppResponse<GetStudentsDTO>(
                                HttpStatus.OK.value(),
                                ApiMessage.SUCCESS,
                                courseService.getStudentsByCourseId(courseId, page, pageSize, sort, sortDir)),
                                HttpStatus.OK);
        }

        @GetMapping("/get-course-details-by-id")
        public ResponseEntity<AppResponse<CourseDTO>> getCourseDetailsById(
                        @RequestParam(value = "courseId") Long courseId) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return new ResponseEntity<AppResponse<CourseDTO>>(new AppResponse<CourseDTO>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, courseService.getCourseByIdWithMembers(courseId)), HttpStatus.OK);
        }

        @PostMapping("/create-course")
        public ResponseEntity<AppResponse<CreateCourseResponse>> createCourse(
                        @RequestBody CreateCourseRequest createCourseRequest) {
                // Authentication
                return new ResponseEntity<AppResponse<CreateCourseResponse>>(
                                new AppResponse<CreateCourseResponse>(HttpStatus.OK.value(),
                                                ApiMessage.SUCCESS, courseService.createCourse(createCourseRequest)),
                                HttpStatus.OK);
        }

        // @PutMapping("/update-course")
        // public ResponseEntity<AppResponse<Course>> updateCourse(
        // @RequestParam(value = "courseId") Long courseId,
        // @RequestBody UpdateCourseRequest updateCourseRequest) {
        //
        // Course updatedCourse = courseService.updateCourse(courseId,
        // updateCourseRequest);
        //
        // // if (updatedCourse == null) {
        // // return new ResponseEntity<>(new AppResponse<Course>(
        // // HttpStatus.BAD_REQUEST.value(),
        // // ApiMessage.FAIL,
        // // null), HttpStatus.BAD_REQUEST);
        // // }
        //
        // return new ResponseEntity<>(new AppResponse<Course>(
        // HttpStatus.OK.value(),
        // ApiMessage.SUCCESS,
        // updatedCourse), HttpStatus.OK);
        // }

        @GetMapping("/test-role")
        public ResponseEntity<AppResponse<String>> testRole() {
                // System.out.println("Id: " + jwt.getClaimAsString("scope"));
                // return new AppResponse<String>(HttpStatus.OK.value(), ApiMessage.SUCCESS,
                // jwt.getSubject());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                        Jwt id = (Jwt) authentication.getPrincipal();

                        return new ResponseEntity<AppResponse<String>>(new AppResponse<String>(HttpStatus.OK.value(),
                                        ApiMessage.SUCCESS, id.getSubject()), HttpStatus.OK);
                }
                return new ResponseEntity<AppResponse<String>>(new AppResponse<String>(HttpStatus.OK.value(),
                                ApiMessage.SUCCESS, "Null"), HttpStatus.OK);
        }

        @GetMapping("/get-students-not-in-course")
        public ResponseEntity<AppResponse<GetStudentNotInCourse>> getStudentsNotInCourse(
                        @RequestParam(value = "courseId") Long courseId,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                        @RequestParam(value = "sort", defaultValue = "1") String sort,
                        @RequestParam(value = "sortDir", defaultValue = "ASC") String sortDir) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                GetStudentNotInCourse result = courseService.getStudentsNotInCourse(courseId, page, pageSize, sort,
                                sortDir);
                return new ResponseEntity<>(
                                new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, result),
                                HttpStatus.OK);
        }

    @PostMapping("/update-student-in-courses")
    public ResponseEntity<AppResponse<String>> updateStudentInCourses(@RequestBody UpdateStudentRequest updateStudentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            // Lấy courseId từ updateStudentRequest, mà không cần chuyển đổi
            Long courseId = Long.valueOf(updateStudentRequest.getCourseId());  // courseId là kiểu Long rồi

            // Gọi service để cập nhật sinh viên trong khóa học
            courseService.updateStudentInCourse(updateStudentRequest);

            // Trả về phản hồi thành công
            return new ResponseEntity<>(
                    new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, "Students updated successfully in the course."),
                    HttpStatus.OK
            );
        } catch (AppRuntimeException e) {
            // Trong trường hợp có lỗi đã biết (ngoại lệ tùy chỉnh), trả về thông báo cụ thể
            return new ResponseEntity<>(
                    new AppResponse<>(HttpStatus.BAD_REQUEST.value(), ApiMessage.FAILED, "Error: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            // Trong trường hợp có lỗi không xác định, ghi log và trả về thông báo lỗi chung
            return new ResponseEntity<>(
                    new AppResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ApiMessage.FAILED, "An unexpected error occurred while updating students in the course."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/get-student-not-pageable")
    public ResponseEntity<AppResponse<List<StudentInCreateCourseDTO>>> getStudentNotPageable(
            @RequestParam(value = "courseId") Long courseId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Lấy dữ liệu từ service
        GetStudentsDTO responseDTO = courseService.getAllStudentsByCourseId(courseId);
        List<StudentInCreateCourseDTO> students = responseDTO.getStudents(); // Lấy danh sách sinh viên từ GetStudentsDTO
        return new ResponseEntity<>(
                new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, students),
                HttpStatus.OK);
    }



}
