package com.example.course.controller;

import com.example.course.dto.request.AccessControlRequest;
import com.example.course.dto.request.LoginByEmailRequest;
import com.example.course.dto.request.LoginByPhoneRequest;
import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.response.AccessControlResponse;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.LoginResponse;
import com.example.course.dto.response.RegisterResponse;
import com.example.course.entity.User;
import com.example.course.repository.CourseRepository;
import com.example.course.service.CourseService;
import com.example.course.service.UserService;
import com.example.course.util.ApiMessage;
import com.example.course.util.StringHandler;
import com.example.course.util.constant.AccessControlType;
import com.example.course.util.constant.RoleEnum;
import com.example.course.dto.request.AccessControlRequest;
import com.example.course.dto.request.LoginByEmailRequest;
import com.example.course.dto.request.LoginByPhoneRequest;
import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.response.AccessControlResponse;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.LoginResponse;
import com.example.course.dto.response.RegisterResponse;
import com.example.course.entity.User;
import com.example.course.service.UserService;
import com.example.course.util.ApiMessage;
import com.example.course.util.StringHandler;
import com.example.course.util.constant.AccessControlType;
import com.example.course.util.constant.RoleEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    private boolean hasAuthority(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }

    private boolean hasAuthority(User user, String role) {
        return user.getRoles().stream()
                .anyMatch(roleUser -> roleUser.getRole().getRoleName().equals(role));
    }

    @PostMapping("/access-control")
    public ResponseEntity<AppResponse<AccessControlResponse>> getAccessControl(
            @RequestBody AccessControlRequest accessControlRequest) {
        String url = accessControlRequest.getUrl();

        // Tạo đối tượng AccessControlResponse
        AccessControlResponse response = new AccessControlResponse();

        switch (url) {
            case "/courses":
                handleCourses(url, response);
                break;
            case "/admin/courses":
                handleAdminCourses(url, response);
                break;
            case "/admin/lecturers":
                handleAdminLecturers(url, response);
                break;
            case "/admin/students":
                handleAdminStudents(url, response);
                break;
            case "/admin/guests":
                handleAdminGuests(url, response);
                break;
            case "/admin/administrators":
                handleAdminAdministrators(url, response);
                break;
            case "/admin/subjects":
                handleAdminSubjects(url, response);
                break;
            case "/admin/subjects/create":
                handleAdminSubjectsCreate(url, response);
                break;
            case "/admin/courses/create":
                handleAdminCoursesCreate(url, response);
                break;
            case "/admin/lecturers/create":
                handleAdminLecturersCreate(url, response);
                break;
            case "/admin/students/create":
                handleAdminStudentsCreate(url, response);
                break;
            case "/admin/guests/create":
                handleAdminGuestsCreate(url, response);
                break;
            case "/admin/administrators/create":
                handleAdminAdministratorsCreate(url, response);
                break;

            default:
                if (url.matches("/course/\\d+/infor")) {
                    handleCourseInfor(url, response);
                } else if (url.matches("/course/\\d+/attendance")) {
                    handleCourseAttendance(url, response);
                } else if (url.matches("/course/\\d+/resource")) {
                    handleCourseResource(url, response);
                } else if (url.matches("/course/\\d+/conversation")) {
                    handleCourseConversation(url, response);
                } else if (url.matches("/course/\\d+/resource/assignment/\\d")) {
                    handleCourseResourceAssignment(url, response);
                } else if (url.matches("/user/\\d+")) {
                    handleUser(url, response);
                } else if (url.matches("/admin/course/\\d+/infor")) {
                    handleAdminCourseInfor(url, response);
                } else if (url.matches("/admin/course/\\d+/attendance")) {
                    handleAdminCourseAttendance(url, response);
                } else if (url.matches("/admin/course/\\d+/resource")) {
                    handleAdminCourseResource(url, response);
                } else if (url.matches("/admin/course/\\d+/resource/assignment/\\d")) {
                    handleAdminCourseResourceAssignment(url, response);
                } else if (url.matches("/admin/course/\\d+/conversation")) {
                    handleAdminCourseConversation(url, response);
                } else if (url.matches("/admin/student/\\d+")) { //
                    handleAdminStudent(url, response);
                } else if (url.matches("/admin/subject/\\d+")) { //
                    handleAdminSubject(url, response);
                } else if (url.matches("/admin/lecturer/\\d+")) { //
                    handleAdminLecturer(url, response);
                } else if (url.matches("/admin/guest/\\d+")) { //
                    handleAdminGuest(url, response);
                } else if (url.matches("/admin/administrator/\\d+")) { //
                    handleAdminAdministrator(url, response);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Không hợp lệ
                }
                break;
        }

        // Trả về response
        return new ResponseEntity<>(new AppResponse<>(HttpStatus.OK.value(), ApiMessage.SUCCESS, response),
                HttpStatus.OK);
    }

    private void handleCourses(String url, AccessControlResponse response) { //
        // Logic xử lý cho /courses
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.REDIRECT.getStatus());
            response.setMessage(AccessControlType.REDIRECT.getMessage());
            response.setRedirectUrl("/admin/courses");
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCourses(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/courses
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCoursesCreate(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/courses
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminLecturers(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/lecturers
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminLecturersCreate(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/lecturers
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminStudents(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/students
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminStudentsCreate(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/students
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleCourseInfor(String url, AccessControlResponse response) { //
        // Logic xử lý cho /course/{id}/infor
        String courseId = Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.REDIRECT.getStatus());
            response.setMessage(AccessControlType.REDIRECT.getMessage());
            response.setRedirectUrl("/admin/course/" + courseId + "/infor");
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCourseResourceAssignment(String url, AccessControlResponse response) { //
        // Logic xử lý cho admin/course/{id}/resource/assignment/{id}
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleCourseResourceAssignment(String url, AccessControlResponse response) { //
        // Logic xử lý cho /course/{id}/resource/assignment/{id}
        String courseId = Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        String assignmentId = Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 1));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.REDIRECT.getStatus());
            response.setMessage(AccessControlType.REDIRECT.getMessage());
            response.setRedirectUrl("/admin/course/" + courseId + "/assignment/" + assignmentId);
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleCourseAttendance(String url, AccessControlResponse response) { //
        // Logic xử lý cho /course/{id}/attendance
        String courseId = Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.REDIRECT.getStatus());
            response.setMessage(AccessControlType.REDIRECT.getMessage());
            response.setRedirectUrl("/admin/course/" + courseId + "/attendance");
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleCourseResource(String url, AccessControlResponse response) { //
        // Logic xử lý cho /course/{id}/resource
        String courseId = Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.REDIRECT.getStatus());
            response.setMessage(AccessControlType.REDIRECT.getMessage());
            response.setRedirectUrl("/admin/course/" + courseId + "/resource");
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleCourseConversation(String url, AccessControlResponse response) { //
        // Logic xử lý cho /course/{id}/conversation
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleUser(String url, AccessControlResponse response) { //
        // Logic xử lý cho /user/{id}
        // Admin -> redirect to admin/lectuter/{id} or admin/student/{id}
        String userId = Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.REDIRECT.getStatus());
            response.setMessage(AccessControlType.REDIRECT.getMessage());

            User user = userService.getUserById(Long.valueOf(userId));
            if (user == null) {
                response.setStatus(AccessControlType.NOT_EXIST.getStatus());
                response.setMessage(AccessControlType.NOT_EXIST.getMessage());
            } else {
                if (hasAuthority(user, RoleEnum.STUDENT.getRoleName())) {
                    response.setRedirectUrl("/admin/student/" + userId);
                } else if (hasAuthority(user, RoleEnum.LECTURER.getRoleName())) {
                    response.setRedirectUrl("/admin/lecturer/" + userId);
                } else if (hasAuthority(user, RoleEnum.GUEST.getRoleName())) {
                    response.setRedirectUrl("/admin/guest/" + userId);
                } else if (hasAuthority(user, RoleEnum.ADMIN.getRoleName())) {
                    response.setRedirectUrl("/admin/administrator/" + userId);
                }
            }
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCourseInfor(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/course/{id}/infor
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCourseAttendance(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/course/{id}/attendance
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCourseResource(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/course/{id}/resource
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminCourseConversation(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/course/{id}/conversation
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    // private void handleAdminUser(String url, AccessControlResponse response) {
    // // Logic xử lý cho /admin/user/{id}
    // response.setMessage("Access granted to admin user for role: ");
    // }

    private void handleAdminLecturer(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/lecturer/{id}
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminStudent(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/student/{id}
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminSubject(String url, AccessControlResponse response) { //
        // Logic xử lý cho /admin/student/{id}
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminGuests(String url, AccessControlResponse response) {
        // Logic for handling /admin/guests
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminSubjects(String url, AccessControlResponse response) {
        // Logic for handling /admin/guests
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminSubjectsCreate(String url, AccessControlResponse response) {
        // Logic for handling /admin/guests
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminGuestsCreate(String url, AccessControlResponse response) {
        // Logic for handling /admin/guests
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminAdministrators(String url, AccessControlResponse response) {
        // Logic for handling /admin/administrators
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminAdministratorsCreate(String url, AccessControlResponse response) {
        // Logic for handling /admin/administrators
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminGuest(String url, AccessControlResponse response) {
        // Logic for handling /admin/guest/{id}
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    private void handleAdminAdministrator(String url, AccessControlResponse response) {
        // Logic for handling /admin/administrator/{id}
        // String courseId =
        // Objects.requireNonNull(StringHandler.extractNumberAtIndex(url, 0));
        if (hasAuthority(RoleEnum.ADMIN.getRoleName())) {
            response.setStatus(AccessControlType.OK.getStatus());
            response.setMessage(AccessControlType.OK.getMessage());
        } else if (hasAuthority(RoleEnum.LECTURER.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.STUDENT.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        } else if (hasAuthority(RoleEnum.GUEST.getRoleName())) {
            response.setStatus(AccessControlType.DENIED.getStatus());
            response.setMessage(AccessControlType.DENIED.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AppResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest user) {
        RegisterResponse registerResponse = userService.register(user);
        return new ResponseEntity<AppResponse<RegisterResponse>>(
                new AppResponse<RegisterResponse>(HttpStatus.OK.value(),
                        ApiMessage.SUCCESS, registerResponse),
                HttpStatus.OK);
    }

    @PostMapping("/login-by-email")
    public ResponseEntity<AppResponse<LoginResponse>> loginByEmail(@RequestBody @Valid LoginByEmailRequest user) {
        System.out.println(user.getEmail());
        LoginResponse loginResponse = userService.loginByEmail(user);
        System.out.println(user.getEmail());
        return new ResponseEntity<AppResponse<LoginResponse>>(new AppResponse<LoginResponse>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, loginResponse), HttpStatus.OK);
    }

    @PostMapping("/login-by-phone")
    public ResponseEntity<AppResponse<LoginResponse>> loginByPhone(@RequestBody @Valid LoginByPhoneRequest user) {
        System.out.println(user.getPhone());
        LoginResponse loginResponse = userService.loginByPhone(user);
        System.out.println(user.getPhone());
        return new ResponseEntity<AppResponse<LoginResponse>>(new AppResponse<LoginResponse>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS, loginResponse), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<AppResponse<List<Object[]>>> test() {
        return new ResponseEntity<AppResponse<List<Object[]>>>(new AppResponse<List<Object[]>>(HttpStatus.OK.value(),
                ApiMessage.SUCCESS,  courseService.test(1, 10)), HttpStatus.OK);
    }

}
