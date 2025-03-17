package com.example.course.service;

import com.example.course.dto.request.UpdateStudentRequest;
import com.example.course.dto.request.UpdateCourseRequest;
import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCoursesDTO;
import com.example.course.dto.response.LecturerDTO;
import com.example.course.entity.Lecturer;
import com.example.course.entity.composite.HistoryId;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.LecturerRepository;
import com.example.course.dto.request.CreateCourseRequest;
import com.example.course.dto.request.UpdateCourseRequest;
import com.example.course.dto.response.*;
import com.example.course.entity.*;
import com.example.course.entity.composite.CourseLecturerId;
import com.example.course.entity.composite.CourseStudentId;
import com.example.course.exception.AppRuntimeException;
import com.example.course.repository.*;
import com.example.course.util.constant.ExceptionType;
import jakarta.transaction.Transactional;
import com.example.course.dto.response.CourseCardDTO;
import com.example.course.dto.response.CourseDTO;
import com.example.course.dto.response.GetCoursesDTO;
import com.example.course.dto.response.LecturerDTO;
import com.example.course.entity.Lecturer;
import com.example.course.repository.CourseRepository;
import com.example.course.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import static java.util.stream.Nodes.collect;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LecturerRepository lecturerRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;
    @Autowired
    private CourseLecturerRepository courseLecturerRepository;
    @Autowired
    private CourseScheduleRepository courseScheduleRepository;
    @Autowired
    private HistoryViewRepository historyViewRepository;
    @Autowired
    private UserRepository userRepository;

    public List<CourseDTO> getAllCourses(int page, int size) {
        return courseRepository.getCourses(PageRequest.of(page, size));
    }

    public GetCoursesDTO getCourses(Integer page, Integer pageSize, String sort, String sortDir) {
        // Tạo Pageable từ các tham số được truyền vào
        String sortAttr = getSortAttribute(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<CourseDTO> res = courseRepository.getCourses(pageable);
        res = res.stream()
                .peek(courseDTO -> courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId())))
                .toList();
        return new GetCoursesDTO(res, courseRepository.findAll().size());
    }

    public GetCoursesDTO getCoursesByStudent(Integer page, Integer pageSize, String sort, String sortDir,
            Long studentId) {
        // Tạo Pageable từ các tham số được truyền vào
        String sortAttr = getSortAttribute(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<CourseDTO> res = courseRepository.getCoursesByStudent(pageable, studentId);
        res = res.stream()
                .peek(courseDTO -> courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId())))
                .toList();
        return new GetCoursesDTO(res, courseRepository.findAll().size());
    }

    public GetCoursesDTO getCoursesByLecturer(Integer page, Integer pageSize, String sort, String sortDir,
            Long lecturerId) {
        // Tạo Pageable từ các tham số được truyền vào
        String sortAttr = getSortAttribute(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<CourseDTO> res = courseRepository.getCoursesByLecture(pageable, lecturerId);
        res = res.stream()
                .peek(courseDTO -> courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId())))
                .toList();
        return new GetCoursesDTO(res, courseRepository.findAll().size());
    }

    public CourseDTO getCourseById(Long courseId) {
        // Tạo Pageable từ các tham số được truyền vào
        CourseDTO courseDTO = courseRepository.getCourseById(courseId).orElseThrow(
                () -> new AppRuntimeException(ExceptionType.ENTITY_NOT_FOUND,
                        "Course(ID: " + courseId + ") not found"));
        courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId()));
        return courseDTO;
    }

    public CourseDTO getCourseByIdWithMembers(Long courseId) {
        // Tạo Pageable từ các tham số được truyền vào
        CourseDTO courseDTO = courseRepository.getCourseById(courseId).orElseThrow(
                () -> new AppRuntimeException(ExceptionType.ENTITY_NOT_FOUND,
                        "Course(ID: " + courseId + ") not found"));
        courseDTO.setLecturers(getLecturersByCourseId(courseDTO.getCourseId()));
        // courseDTO.setStudents(getLStudentsByCourseId(courseDTO.getCourseId()));
        courseDTO.setSchedules(getSchedulesByCourseId(courseDTO.getCourseId()));

        return courseDTO;
    }

    public GetLecturersDTO getLecturers(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeLecturers(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<LecturerInCreateCourseDTO> lecturers = lecturerRepository.findLecturers(pageable);
        Integer total = lecturerRepository.findAll().size();
        return new GetLecturersDTO(lecturers, total);
    }

    public GetSubjectsDTO getSubjects(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeSubjects(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<SubjectInCreateCourseDTO> subjects = subjectRepository.findSubjects(pageable);
        Integer total = subjectRepository.findAll().size();
        return new GetSubjectsDTO(subjects, total);
    }

    public GetStudentsDTO getStudents(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeStudents(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<StudentInCreateCourseDTO> students = studentRepository.findStudents(pageable);
        Integer total = studentRepository.findAll().size();
        return new GetStudentsDTO(students, total);
    }

    public GetStudentsDTO getStudentsByCourseId(Long courseId, Integer page, Integer pageSize, String sort,
            String sortDir) {
        String sortAttr = getSortAttributeStudents(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<StudentInCreateCourseDTO> students = courseStudentRepository.findByCourseId(courseId, pageable);
        Integer total = courseStudentRepository.countStudentsByCourseId(courseId);
        return new GetStudentsDTO(students, total);
    }

    public GetStudentNotInCourse getStudentsNotInCourse(Long courseId, Integer page, Integer pageSize, String sort,
            String sortDir) {
        String sortAttr = getSortAttributeStudents(sort);
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        // Lấy danh sách sinh viên không tham gia khóa học
        List<StudentNotInCourseDTO> students = courseStudentRepository.findStudentsNotInCourse(courseId, pageable);

        // Tổng số sinh viên không tham gia khóa học
        Integer total = courseStudentRepository.countStudentsNotInCourse(courseId);

        return new GetStudentNotInCourse(students, total);
    }

    public GetRoomsDTO getRooms(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttributeRooms(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<RoomDTO> rooms = roomRepository.findRooms(pageable);
        Integer total = roomRepository.findAll().size();
        return new GetRoomsDTO(rooms, total);
    }

    private String getSortAttribute(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "c.courseId");
        sortMapper.put(2, "c.subject.subjectName");
        sortMapper.put(3, "c.startDate");
        sortMapper.put(4, "c.endDate");
        sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    private String getSortAttributeLecturers(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "lec.lecturerId");
        sortMapper.put(2, "u.username");
        // sortMapper.put(3, "c.startDate");
        // sortMapper.put(4, "c.endDate");
        // sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    private String getSortAttributeStudents(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "s.studentId");
        sortMapper.put(2, "u.username");
        // sortMapper.put(3, "c.startDate");
        // sortMapper.put(4, "c.endDate");
        // sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    private String getSortAttributeSubjects(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "s.subjectId");
        sortMapper.put(2, "s.subjectName");
        // sortMapper.put(3, "c.startDate");
        // sortMapper.put(4, "c.endDate");
        // sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    private String getSortAttributeRooms(String sort) {
        Map<Integer, String> sortMapper = new HashMap<>();
        sortMapper.put(1, "r.roomId");
        sortMapper.put(2, "r.roomName");
        // sortMapper.put(3, "c.startDate");
        // sortMapper.put(4, "c.endDate");
        // sortMapper.put(5, "COUNT(s)");

        return sortMapper.get(Integer.valueOf(sort));
    }

    @Transactional
    public CreateCourseResponse createCourse(CreateCourseRequest courseRequest) {
        // Authorization
        // Business
        Long subjectId = courseRequest.getSubjectId();
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                        "Subject(ID: " + subjectId + ") does not exist"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // hoặc định dạng bạn muốn

        Course course = Course.builder()
                .startDate(LocalDate.parse(courseRequest.getStartDate(), formatter))
                .endDate(LocalDate.parse(courseRequest.getEndDate(), formatter))
                .subject(subject)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        course = courseRepository.save(course);

        addStudentToCourse(course.getCourseId(), courseRequest.getStudentIds());

        addLecturerToCourse(course.getCourseId(), courseRequest.getLecturerIds());

        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // hoặc định dạng bạn
                                                                                               // muốn

        Course finalCourse = course;
        List<CourseSchedule> courseScheduleList = courseRequest.getSchedules()
                .stream().map((scheduleRequestDTO -> {
                    return CourseSchedule.builder()
                            .course(finalCourse) // Truyền đối tượng course vào CourseSchedule
                            .startTime(LocalDateTime.parse(scheduleRequestDTO.getStartTime(), formatterDateTime)) // Giả
                                                                                                                  // sử
                                                                                                                  // scheduleRequestDTO
                                                                                                                  // có
                                                                                                                  // startTime
                            .endTime(LocalDateTime.parse(scheduleRequestDTO.getEndTime(), formatterDateTime)) // Giả sử
                                                                                                              // scheduleRequestDTO
                                                                                                              // có
                                                                                                              // endTime
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build();
                })).toList();
        courseScheduleRepository.saveAll(courseScheduleList);

        System.out.println("Courses in subject object: " + subject.getCourses().size());

        return null;
    }

    public void addStudentToCourse(Long courseId, List<Long> studentIds) {
        System.out.println("Course: " + courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                        "Course(ID: " + courseId + ") does not exist"));

        List<Student> students = studentRepository.findAllById(studentIds);

        Set<Long> foundStudentIds = students.stream()
                .map(Student::getStudentId)
                .collect(Collectors.toSet());

        List<Long> nonExistentIds = studentIds.stream()
                .filter(id -> !foundStudentIds.contains(id))
                .toList();

        if (!nonExistentIds.isEmpty()) {
            throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                    "Students(ID: " + nonExistentIds.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", ")) + ") do not exist");
        }

        // Kiểm tra trùng lặp trước khi thêm vào courseStudentRepository
        for (Student student : students) {
            CourseStudentId courseStudentId = new CourseStudentId(courseId, student.getStudentId());
            Optional<CourseStudent> existingCourseStudent = courseStudentRepository.findById(courseStudentId);
            if (existingCourseStudent.isEmpty()) {
                CourseStudent courseStudent = CourseStudent.builder()
                        .id(courseStudentId)
                        .course(course)
                        .student(student)
                        .build();
                courseStudentRepository.save(courseStudent);
            }
        }
    }

    @Transactional
    public void addLecturerToCourse(Long courseId, List<Long> lecturerIds) {
        // Tìm khóa học từ courseId
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                        "Course(ID: " + courseId + ") does not exist"));

        // Tìm các giảng viên theo lecturerIds
        List<Lecturer> lecturers = lecturerRepository.findAllById(lecturerIds);

        // Lấy danh sách các id giảng viên đã tìm thấy
        Set<Long> foundLecturerIds = lecturers.stream()
                .map(Lecturer::getLecturerId) // Giả sử Lecturer có phương thức getLecturerId()
                .collect(Collectors.toSet());

        // Kiểm tra các giảng viên không tồn tại trong database
        List<Long> nonExistentIds = lecturerIds.stream()
                .filter(id -> !foundLecturerIds.contains(id))
                .toList();

        // Nếu có giảng viên không tồn tại, ném ngoại lệ
        if (!nonExistentIds.isEmpty()) {
            throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                    "Lecturers(ID: " + nonExistentIds.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", ")) + ") do not exist");
        }

        // Lặp qua các giảng viên đã tìm thấy và kiểm tra xem có tồn tại mối quan hệ
        // CourseLecturer chưa
        for (Lecturer lecturer : lecturers) {
            CourseLecturerId courseLecturerId = new CourseLecturerId(courseId, lecturer.getLecturerId()); // Tạo id khóa
                                                                                                          // học - giảng
                                                                                                          // viên
            Optional<CourseLecturer> existingCourseLecturer = courseLecturerRepository.findById(courseLecturerId);

            if (existingCourseLecturer.isEmpty()) {
                // Nếu chưa có mối quan hệ, tạo mới và lưu vào database
                CourseLecturer courseLecturer = CourseLecturer.builder()
                        .id(courseLecturerId)
                        .course(course)
                        .lecturer(lecturer)
                        .build();
                courseLecturerRepository.save(courseLecturer);
            }
        }
    }

    public List<LecturerDTO> getLecturersByCourseId(Long courseId) {
        return lecturerRepository.findByCourseId(courseId);
    }

    // public List<StudentInCreateCourseDTO> getLStudentsByCourseId(Long courseId) {
    // return courseStudentRepository.findByCourseId(courseId);
    // }

    public List<ScheduleDTO> getSchedulesByCourseId(Long courseId) {
        return courseScheduleRepository.findByCourseId(courseId);
    }

    public List<Object[]> test(Integer page_index, Integer limit) {
        Pageable pageable = PageRequest.of(page_index - 1, limit); // Trang đầu tiên, 5 kết quả
        return courseRepository.test(pageable);
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

    public GetCourseCardDTO getAllCourseCards(Integer page, Integer pageSize, String sort, String sortDir) {
        String sortAttr = getSortAttribute(sort); // Hàm lấy thuộc tính sắp xếp tương ứng từ số
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, sortAttr);

        List<CourseCardDTO> courses = courseRepository.getAllCourseCards(pageable);
        courses.forEach(course -> {
            // Tính duration theo số tháng giữa startDate và endDate
            long months = ChronoUnit.MONTHS.between(course.getStartDate(), course.getEndDate());
            course.setDuration(months + " tháng");
            List<LecturerDTO> lecturers = getLecturersByCourseId(course.getCourseId());
            String lecturerNames = lecturers.stream()
                    .map(LecturerDTO::getUsername) // Sử dụng đúng getter
                    .collect(Collectors.joining(", "));
            course.setAuthor(lecturerNames); // Gán tên giảng viên vào CourseCardDTO
        });

        return new GetCourseCardDTO(courses, courseRepository.findAll().size());
    }

    public List<CourseCardDTO> getCourseCardsById(List<Long> courseIdList) {
        List<CourseCardDTO> courses = courseRepository.getCourseCardsById(courseIdList);

        courses.forEach(course -> {
            // Tính duration theo số tháng giữa startDate và endDate
            long months = ChronoUnit.MONTHS.between(course.getStartDate(), course.getEndDate());
            course.setDuration(months + " tháng");
            List<LecturerDTO> lecturers = getLecturersByCourseId(course.getCourseId());
            String lecturerNames = lecturers.stream()
                    .map(LecturerDTO::getUsername) // Sử dụng đúng getter
                    .collect(Collectors.joining(", "));
            course.setAuthor(lecturerNames); // Gán tên giảng viên vào CourseCardDTO
        });

        return courses;
    }


    public void updateStudentInCourse(UpdateStudentRequest request) {
        if (request.getCourseId() == null || request.getStudentIds() == null || request.getStudentIds().isEmpty()) {
            System.out.println("Request received: CourseId=" + request.getCourseId());
            System.out.println("Student IDs: " + request.getStudentIds());
            throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                    "Course ID or Student IDs cannot be null or empty.");
        }

        // Fetch courseId and studentIds from the request object
        Long courseId = Long.valueOf(request.getCourseId());  // Convert courseId to Long
        List<Long> studentIds = request.getStudentIds();

        try {
            System.out.println("Updating students in course: " + courseId);

            // Kiểm tra khóa học có tồn tại
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                            "Course(ID: " + courseId + ") does not exist"));

            System.out.println("Course found: " + courseId);

            // Lấy danh sách các sinh viên từ studentIds
            List<Student> students = studentRepository.findAllById(studentIds);

            // Kiểm tra xem sinh viên có tồn tại hay không
            Set<Long> foundStudentIds = students.stream()
                    .map(Student::getStudentId)
                    .collect(Collectors.toSet());

            List<Long> nonExistentIds = studentIds.stream()
                    .filter(id -> !foundStudentIds.contains(id))
                    .collect(Collectors.toList());

            if (!nonExistentIds.isEmpty()) {
                throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION,
                        "Students(ID: " + nonExistentIds.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(", ")) + ") do not exist");
            }

            // Lấy danh sách sinh viên hiện tại trong khóa học
            Pageable pageable = Pageable.unpaged(); // Không phân trang
            List<StudentInCreateCourseDTO> currentStudents = courseStudentRepository.findByCourseId(courseId, pageable);
            Set<Long> currentStudentIds = currentStudents.stream()
                    .map(StudentInCreateCourseDTO::getStudentId)
                    .collect(Collectors.toSet());

            // 1. Thêm sinh viên mới vào khóa học nếu chưa có
            for (Student student : students) {
                if (!currentStudentIds.contains(student.getStudentId())) {
                    CourseStudentId courseStudentId = new CourseStudentId(courseId, student.getStudentId());
                    CourseStudent newCourseStudent = CourseStudent.builder()
                            .id(courseStudentId)
                            .course(course)
                            .student(student)
                            .build();
                    courseStudentRepository.save(newCourseStudent);
                    System.out.println("Added student ID: " + student.getStudentId());
                }
            }

            // 2. Xóa sinh viên không có trong danh sách request
            for (StudentInCreateCourseDTO currentStudent : currentStudents) {
                if (!foundStudentIds.contains(currentStudent.getStudentId())) {
                    CourseStudentId courseStudentId = new CourseStudentId(courseId, currentStudent.getStudentId());
                    courseStudentRepository.deleteById(courseStudentId);
                    System.out.println("Removed student ID: " + currentStudent.getStudentId());
                }
            }

        } catch (Exception e) {
            // Log chi tiết lỗi và throw lại exception để có thể theo dõi lỗi từ bên ngoài
            System.err.println("An error occurred while updating students in the course: " + e.getMessage());
            e.printStackTrace();  // Log lỗi chi tiết
            throw new AppRuntimeException(ExceptionType.INTERNAL_SERVER_ERROR, "An unexpected error occurred while updating students in the course.");
        }
    }


    public GetStudentsDTO getAllStudentsByCourseId(Long courseId) {
        List<StudentInCreateCourseDTO> students = courseStudentRepository.findByStudentCourseId(courseId);
        Integer total = courseStudentRepository.countStudentsByCourseId(courseId);
        return new GetStudentsDTO(students, total);
    }

    public List<HistoryView> getHistoryView(Long userId) {
        Pageable pageable = PageRequest.of(0, 5); // Trang đầu tiên, 5 kết quả
        return historyViewRepository.getHistoryViewsByUserId(userId, pageable);
    }

    public Boolean historyView(Long userId, Long courseId) {
        HistoryView hv = new HistoryView();

        hv.setCreatedDate(LocalDateTime.now());
        hv.setUpdatedDate(LocalDateTime.now());

        System.out.println(courseId);
        System.out.println(userId);

        Course course = courseRepository.findCourseByCourseId(courseId);
//        course.setCourseId(courseId);
        hv.setCourse(course);

        User user = userRepository.findByUserId(userId);
//        user.setUserId(userId);
        hv.setUser(user);


        try {
            historyViewRepository.save(hv);
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new AppRuntimeException(ExceptionType.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            throw new AppRuntimeException(ExceptionType.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
