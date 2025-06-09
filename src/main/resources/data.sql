-- Insert or update teachers
INSERT INTO teachers (teacher_id, name, email, phone, department, position, avatar_url)
VALUES 
('T001', 'Dr. John Smith', 'john.smith@uit.edu.vn', '0123456789', 'Computer Science', 'Professor', 'https://example.com/avatars/teacher1.jpg'),
('T002', 'Dr. Mary Johnson', 'mary.johnson@uit.edu.vn', '0987654321', 'Computer Science', 'Associate Professor', 'https://example.com/avatars/teacher2.jpg'),
('T003', 'Dr. David Brown', 'david.brown@uit.edu.vn', '0369852147', 'Computer Science', 'Assistant Professor', 'https://example.com/avatars/teacher3.jpg')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
email = VALUES(email),
phone = VALUES(phone),
department = VALUES(department),
position = VALUES(position),
avatar_url = VALUES(avatar_url);

-- Insert or update students
INSERT INTO students (student_id, name, email, password, dob, avatar_url, class_name)
VALUES 
('20520123', 'Nguyễn Văn A', 'nva@student.uit.edu.vn', '123456', '2002-01-01', 'https://example.com/avatar1.jpg', 'IT1'),
('20520124', 'Trần Thị B', 'ttb@student.uit.edu.vn', '123456', '2002-02-02', 'https://example.com/avatar2.jpg', 'IT2'),
('20520125', 'Lê Văn C', 'lvc@student.uit.edu.vn', '123456', '2002-03-03', 'https://example.com/avatar3.jpg', 'IT1')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
email = VALUES(email),
password = VALUES(password),
dob = VALUES(dob),
avatar_url = VALUES(avatar_url),
class_name = VALUES(class_name);

-- Insert or update semesters
INSERT INTO semesters (semester_id, name, gpa, total_credits, completed_courses, in_progress_courses)
VALUES 
('HK1_2024', 'HK1 2024-2025', 3.75, 45, 15, 4),
('HK2_2024', 'HK2 2024-2025', 3.50, 42, 14, 3)
ON DUPLICATE KEY UPDATE
name = VALUES(name),
gpa = VALUES(gpa),
total_credits = VALUES(total_credits),
completed_courses = VALUES(completed_courses),
in_progress_courses = VALUES(in_progress_courses);

-- Insert or update courses
INSERT INTO courses (course_id, course_name, credits, room, schedule, semester, status, description, department, teacher_id)
VALUES 
('CS101', 'Introduction to Programming', 3, 'A101', 'Monday 8:00-11:00', 'HK1_2024', 'Active', 'Basic programming concepts', 'Computer Science', 'T001'),
('CS102', 'Data Structures', 4, 'B202', 'Wednesday 13:00-16:00', 'HK1_2024', 'Active', 'Data structures and algorithms', 'Computer Science', 'T002'),
('CS103', 'Database Systems', 4, 'C303', 'Friday 8:00-11:00', 'HK1_2024', 'Active', 'Database design and management', 'Computer Science', 'T003'),
('CS104', 'Web Development', 3, 'D404', 'Tuesday 8:00-11:00', 'HK1_2024', 'Active', 'Web development with modern frameworks', 'Computer Science', 'T001'),
('CS105', 'Mobile Development', 3, 'E505', 'Thursday 13:00-16:00', 'HK1_2024', 'Active', 'Mobile app development', 'Computer Science', 'T002')
ON DUPLICATE KEY UPDATE
course_name = VALUES(course_name),
credits = VALUES(credits),
room = VALUES(room),
schedule = VALUES(schedule),
semester = VALUES(semester),
status = VALUES(status),
description = VALUES(description),
department = VALUES(department),
teacher_id = VALUES(teacher_id);

-- Insert or update course enrollments
INSERT INTO course_student (student_id, course_id)
VALUES 
('20520123', 'CS101'),
('20520123', 'CS102'),
('20520123', 'CS103')
ON DUPLICATE KEY UPDATE
student_id = VALUES(student_id),
course_id = VALUES(course_id);

-- Insert or update assignment deadlines with various timeframes
INSERT INTO assignment_deadlines (
    id, course_id, title, description, deadline_date, submission_type, priority,
    max_points, weight_percentage, is_group_work, submission_url, status, created_at, updated_at
) VALUES 
-- CS101 Deadlines
(1, 'CS101', 'Lab 1: Basic Syntax', 'Practice basic programming syntax', 
 DATE_ADD(NOW(), INTERVAL 15 MINUTE), 'ASSIGNMENT', 'HIGH', 10.0, 10.0, false,
 'https://example.com/submit/cs101/lab1', 'PENDING', NOW(), NOW()),
(2, 'CS101', 'Assignment 1: Variables', 'Working with variables and data types',
 DATE_SUB(NOW(), INTERVAL 1 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs101/assignment1', 'OVERDUE', 
 DATE_SUB(NOW(), INTERVAL 5 DAY), NOW()),
(3, 'CS101', 'Quiz 1: Control Structures', 'Test on if-else and loops',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 'EXAM', 'MEDIUM', 10.0, 10.0, false,
 'https://example.com/submit/cs101/quiz1', 'PENDING', NOW(), NOW()),
(4, 'CS101', 'Project 1: Calculator', 'Build a simple calculator application',
 DATE_ADD(NOW(), INTERVAL 4 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs101/project1', 'PENDING', NOW(), NOW()),
(5, 'CS101', 'Lab 2: Functions', 'Practice function implementation',
 DATE_ADD(NOW(), INTERVAL 2 DAY), 'ASSIGNMENT', 'MEDIUM', 10.0, 10.0, false,
 'https://example.com/submit/cs101/lab2', 'PENDING', NOW(), NOW()),
(6, 'CS101', 'Assignment 2: Arrays', 'Working with arrays and collections',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs101/assignment2', 'PENDING', NOW(), NOW()),
(7, 'CS101', 'Midterm Exam', 'Midterm examination covering all topics',
 DATE_ADD(NOW(), INTERVAL 5 DAY), 'EXAM', 'HIGH', 30.0, 30.0, false,
 'https://example.com/submit/cs101/midterm', 'PENDING', NOW(), NOW()),

-- CS102 Deadlines
(8, 'CS102', 'Lab 2: Linked Lists', 'Implement linked list operations',
 DATE_ADD(NOW(), INTERVAL 30 MINUTE), 'ASSIGNMENT', 'HIGH', 10.0, 10.0, false,
 'https://example.com/submit/cs102/lab2', 'PENDING', NOW(), NOW()),
(9, 'CS102', 'Assignment 2: Trees', 'Implement binary search tree operations',
 DATE_SUB(NOW(), INTERVAL 2 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs102/assignment2', 'OVERDUE', 
 DATE_SUB(NOW(), INTERVAL 7 DAY), NOW()),
(10, 'CS102', 'Project 1: Data Structures', 'Implement a data structure library',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs102/project1', 'PENDING', NOW(), NOW()),
(11, 'CS102', 'Lab 3: Stacks and Queues', 'Implement stack and queue operations',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 'ASSIGNMENT', 'MEDIUM', 10.0, 10.0, false,
 'https://example.com/submit/cs102/lab3', 'PENDING', NOW(), NOW()),
(12, 'CS102', 'Assignment 3: Graphs', 'Implement graph algorithms',
 DATE_ADD(NOW(), INTERVAL 2 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs102/assignment3', 'PENDING', NOW(), NOW()),
(13, 'CS102', 'Quiz 1: Data Structures', 'Test on basic data structures',
 DATE_ADD(NOW(), INTERVAL 4 DAY), 'EXAM', 'MEDIUM', 10.0, 10.0, false,
 'https://example.com/submit/cs102/quiz1', 'PENDING', NOW(), NOW()),
(14, 'CS102', 'Project 2: Algorithm Analysis', 'Analyze and implement algorithms',
 DATE_ADD(NOW(), INTERVAL 6 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs102/project2', 'PENDING', NOW(), NOW()),

-- CS103 Deadlines
(15, 'CS103', 'Lab 3: SQL Queries', 'Practice SQL queries',
 DATE_ADD(NOW(), INTERVAL 45 MINUTE), 'ASSIGNMENT', 'HIGH', 10.0, 10.0, false,
 'https://example.com/submit/cs103/lab3', 'PENDING', NOW(), NOW()),
(16, 'CS103', 'Assignment 2: Database Design', 'Design a database schema',
 DATE_SUB(NOW(), INTERVAL 3 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs103/assignment2', 'OVERDUE', 
 DATE_SUB(NOW(), INTERVAL 8 DAY), NOW()),
(17, 'CS103', 'Project 1: E-commerce DB', 'Design and implement an e-commerce database',
 DATE_ADD(NOW(), INTERVAL 2 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs103/project1', 'PENDING', NOW(), NOW()),
(18, 'CS103', 'Lab 4: Joins and Subqueries', 'Practice complex SQL queries',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 'ASSIGNMENT', 'MEDIUM', 10.0, 10.0, false,
 'https://example.com/submit/cs103/lab4', 'PENDING', NOW(), NOW()),
(19, 'CS103', 'Assignment 3: Transactions', 'Implement transaction management',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs103/assignment3', 'PENDING', NOW(), NOW()),
(20, 'CS103', 'Quiz 1: Database Concepts', 'Test on database fundamentals',
 DATE_ADD(NOW(), INTERVAL 4 DAY), 'EXAM', 'MEDIUM', 10.0, 10.0, false,
 'https://example.com/submit/cs103/quiz1', 'PENDING', NOW(), NOW()),
(21, 'CS103', 'Project 2: Database Optimization', 'Optimize database performance',
 DATE_ADD(NOW(), INTERVAL 5 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs103/project2', 'PENDING', NOW(), NOW()),

-- New Deadlines for CS101 (3 days to 1 hour)
(22, 'CS101', 'Bài tập lớn - Phần 1', 'Hoàn thành phần thiết kế và phân tích yêu cầu',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs101/project2', 'PENDING', NOW(), NOW()),
(23, 'CS101', 'Bài tập tuần 5', 'Làm bài tập về cấu trúc dữ liệu và giải thuật',
 DATE_ADD(NOW(), INTERVAL 2 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs101/assignment3', 'PENDING', NOW(), NOW()),
(24, 'CS101', 'Bài kiểm tra giữa kỳ', 'Kiểm tra kiến thức từ tuần 1-6',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 'EXAM', 'URGENT', 30.0, 30.0, false,
 'https://example.com/submit/cs101/midterm2', 'PENDING', NOW(), NOW()),
(25, 'CS101', 'Bài tập nhóm - Báo cáo tiến độ', 'Nộp báo cáo tiến độ dự án',
 DATE_ADD(NOW(), INTERVAL 12 HOUR), 'PROJECT', 'HIGH', 10.0, 10.0, true,
 'https://example.com/submit/cs101/progress', 'PENDING', NOW(), NOW()),
(26, 'CS101', 'Bài tập nhanh - Quiz', 'Kiểm tra kiến thức nhanh về bài học hôm nay',
 DATE_ADD(NOW(), INTERVAL 1 HOUR), 'EXAM', 'URGENT', 5.0, 5.0, false,
 'https://example.com/submit/cs101/quickquiz', 'PENDING', NOW(), NOW()),

-- New Deadlines for CS102 (3 days to 1 hour)
(27, 'CS102', 'Bài tập lớn - Phần 1', 'Hoàn thành phần thiết kế và phân tích yêu cầu',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs102/project3', 'PENDING', NOW(), NOW()),
(28, 'CS102', 'Bài tập tuần 5', 'Làm bài tập về cấu trúc dữ liệu và giải thuật',
 DATE_ADD(NOW(), INTERVAL 2 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs102/assignment4', 'PENDING', NOW(), NOW()),
(29, 'CS102', 'Bài kiểm tra giữa kỳ', 'Kiểm tra kiến thức từ tuần 1-6',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 'EXAM', 'URGENT', 30.0, 30.0, false,
 'https://example.com/submit/cs102/midterm2', 'PENDING', NOW(), NOW()),
(30, 'CS102', 'Bài tập nhóm - Báo cáo tiến độ', 'Nộp báo cáo tiến độ dự án',
 DATE_ADD(NOW(), INTERVAL 12 HOUR), 'PROJECT', 'HIGH', 10.0, 10.0, true,
 'https://example.com/submit/cs102/progress', 'PENDING', NOW(), NOW()),
(31, 'CS102', 'Bài tập nhanh - Quiz', 'Kiểm tra kiến thức nhanh về bài học hôm nay',
 DATE_ADD(NOW(), INTERVAL 1 HOUR), 'EXAM', 'URGENT', 5.0, 5.0, false,
 'https://example.com/submit/cs102/quickquiz', 'PENDING', NOW(), NOW()),

-- New Deadlines for CS103 (3 days to 1 hour)
(32, 'CS103', 'Bài tập lớn - Phần 1', 'Hoàn thành phần thiết kế và phân tích yêu cầu',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 'PROJECT', 'HIGH', 20.0, 20.0, true,
 'https://example.com/submit/cs103/project3', 'PENDING', NOW(), NOW()),
(33, 'CS103', 'Bài tập tuần 5', 'Làm bài tập về cấu trúc dữ liệu và giải thuật',
 DATE_ADD(NOW(), INTERVAL 2 DAY), 'HOMEWORK', 'HIGH', 15.0, 15.0, false,
 'https://example.com/submit/cs103/assignment4', 'PENDING', NOW(), NOW()),
(34, 'CS103', 'Bài kiểm tra giữa kỳ', 'Kiểm tra kiến thức từ tuần 1-6',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 'EXAM', 'URGENT', 30.0, 30.0, false,
 'https://example.com/submit/cs103/midterm2', 'PENDING', NOW(), NOW()),
(35, 'CS103', 'Bài tập nhóm - Báo cáo tiến độ', 'Nộp báo cáo tiến độ dự án',
 DATE_ADD(NOW(), INTERVAL 12 HOUR), 'PROJECT', 'HIGH', 10.0, 10.0, true,
 'https://example.com/submit/cs103/progress', 'PENDING', NOW(), NOW()),
(36, 'CS103', 'Bài tập nhanh - Quiz', 'Kiểm tra kiến thức nhanh về bài học hôm nay',
 DATE_ADD(NOW(), INTERVAL 1 HOUR), 'EXAM', 'URGENT', 5.0, 5.0, false,
 'https://example.com/submit/cs103/quickquiz', 'PENDING', NOW(), NOW())
ON DUPLICATE KEY UPDATE
course_id = VALUES(course_id),
title = VALUES(title),
description = VALUES(description),
deadline_date = VALUES(deadline_date),
submission_type = VALUES(submission_type),
priority = VALUES(priority),
max_points = VALUES(max_points),
weight_percentage = VALUES(weight_percentage),
is_group_work = VALUES(is_group_work),
submission_url = VALUES(submission_url),
status = VALUES(status),
created_at = VALUES(created_at),
updated_at = VALUES(updated_at);

-- Insert or update deadline submissions
INSERT INTO deadline_submissions (
    deadline_id, student_id, submit_date, status, grade, feedback
) VALUES 
-- CS101 Submissions
(1, '20520123', DATE_SUB(NOW(), INTERVAL 1 DAY), 'SUBMITTED', 9.0, 'Good work'),
(2, '20520123', DATE_SUB(NOW(), INTERVAL 2 DAY), 'GRADED', 8.5, 'Well done'),
(3, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(4, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(5, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(6, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(7, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),

-- CS102 Submissions
(8, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(9, '20520123', DATE_SUB(NOW(), INTERVAL 3 DAY), 'GRADED', 7.5, 'Good effort'),
(10, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(11, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(12, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(13, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(14, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),

-- CS103 Submissions
(15, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(16, '20520123', DATE_SUB(NOW(), INTERVAL 4 DAY), 'GRADED', 8.0, 'Well done'),
(17, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(18, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(19, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(20, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL),
(21, '20520123', NULL, 'NOT_SUBMITTED', NULL, NULL);

-- Insert or update course lessons for the entire month
INSERT INTO course_lessons (
    course_id, title, description, lesson_date, duration
) VALUES 
-- CS101 Lessons
('CS101', 'Introduction to Programming', 'Overview of programming concepts', 
 DATE_SUB(NOW(), INTERVAL 7 DAY), 180),
('CS101', 'Variables and Data Types', 'Understanding variables and data types',
 DATE_SUB(NOW(), INTERVAL 5 DAY), 180),
('CS101', 'Control Structures', 'If-else statements and loops',
 DATE_SUB(NOW(), INTERVAL 3 DAY), 180),
('CS101', 'Functions and Methods', 'Creating and using functions',
 DATE_SUB(NOW(), INTERVAL 1 DAY), 180),
('CS101', 'Arrays and Collections', 'Working with arrays and lists',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 180),
('CS101', 'Object-Oriented Basics', 'Classes and objects introduction',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 180),
('CS101', 'Exception Handling', 'Error handling and exceptions',
 DATE_ADD(NOW(), INTERVAL 5 DAY), 180),
('CS101', 'File I/O Operations', 'Reading and writing files',
 DATE_ADD(NOW(), INTERVAL 7 DAY), 180),

-- CS102 Lessons
('CS102', 'Introduction to Data Structures', 'Overview of data structures',
 DATE_SUB(NOW(), INTERVAL 7 DAY), 180),
('CS102', 'Arrays and Linked Lists', 'Working with arrays and linked lists',
 DATE_SUB(NOW(), INTERVAL 5 DAY), 180),
('CS102', 'Stacks and Queues', 'Stack and queue implementations',
 DATE_SUB(NOW(), INTERVAL 3 DAY), 180),
('CS102', 'Trees and Graphs', 'Tree and graph data structures',
 DATE_SUB(NOW(), INTERVAL 1 DAY), 180),
('CS102', 'Sorting Algorithms', 'Various sorting techniques',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 180),
('CS102', 'Searching Algorithms', 'Linear and binary search',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 180),
('CS102', 'Hash Tables', 'Hash table implementation and usage',
 DATE_ADD(NOW(), INTERVAL 5 DAY), 180),
('CS102', 'Advanced Data Structures', 'Advanced data structure concepts',
 DATE_ADD(NOW(), INTERVAL 7 DAY), 180),

-- CS103 Lessons
('CS103', 'Database Fundamentals', 'Introduction to databases',
 DATE_SUB(NOW(), INTERVAL 7 DAY), 180),
('CS103', 'SQL Basics', 'Basic SQL commands and queries',
 DATE_SUB(NOW(), INTERVAL 5 DAY), 180),
('CS103', 'Database Design', 'ER diagrams and normalization',
 DATE_SUB(NOW(), INTERVAL 3 DAY), 180),
('CS103', 'Advanced SQL', 'Complex queries and joins',
 DATE_SUB(NOW(), INTERVAL 1 DAY), 180),
('CS103', 'Transactions', 'ACID properties and transactions',
 DATE_ADD(NOW(), INTERVAL 1 DAY), 180),
('CS103', 'Indexing', 'Database indexing and optimization',
 DATE_ADD(NOW(), INTERVAL 3 DAY), 180),
('CS103', 'Stored Procedures', 'Creating and using stored procedures',
 DATE_ADD(NOW(), INTERVAL 5 DAY), 180),
('CS103', 'Database Security', 'Security and access control',
 DATE_ADD(NOW(), INTERVAL 7 DAY), 180);

-- Insert or update course notifications
INSERT INTO course_notifications (
    course_id, title, content, created_at
) VALUES 
('CS101', 'Welcome to CS101', 'Welcome to Introduction to Programming course', 
 DATE_SUB(NOW(), INTERVAL 2 DAY)),
('CS101', 'Assignment 1 Posted', 'Assignment 1 is now available', 
 DATE_SUB(NOW(), INTERVAL 1 DAY)),
('CS102', 'Welcome to CS102', 'Welcome to Data Structures course',
 DATE_SUB(NOW(), INTERVAL 2 DAY)),
('CS102', 'Project Guidelines', 'Project 1 guidelines are now available',
 DATE_SUB(NOW(), INTERVAL 1 DAY));

-- Insert or update academic progress
INSERT INTO academic_progress (
    student_id, total_required_credits, completed_credits, remaining_credits, estimated_graduation
) VALUES 
('20520123', 145, 45, 100, '2026-06'),
('20520124', 145, 42, 103, '2026-06'),
('20520125', 145, 39, 106, '2026-06')
ON DUPLICATE KEY UPDATE
total_required_credits = VALUES(total_required_credits),
completed_credits = VALUES(completed_credits),
remaining_credits = VALUES(remaining_credits),
estimated_graduation = VALUES(estimated_graduation);

-- Insert or update course grades
INSERT INTO course_grades (
    course_id, semester_id, student_id, midterm, practice, final_grade, average, letter_grade, status, last_updated
) VALUES 
('CS101', 'HK1_2024', '20520123', 8.5, 9.0, 8.7, 8.8, 'A', 'completed', NOW()),
('CS102', 'HK1_2024', '20520123', 7.5, 8.0, 7.8, 7.7, 'B+', 'completed', NOW()),
('CS103', 'HK1_2024', '20520123', 8.0, 8.5, 8.2, 8.3, 'B+', 'completed', NOW())
ON DUPLICATE KEY UPDATE
midterm = VALUES(midterm),
practice = VALUES(practice),
final_grade = VALUES(final_grade),
average = VALUES(average),
letter_grade = VALUES(letter_grade),
status = VALUES(status),
last_updated = VALUES(last_updated);

-- Insert or update schedules
INSERT INTO schedules (course_id, day_of_week, start_time, end_time, room)
VALUES 
-- Monday schedules
('CS101', 'MONDAY', '08:00:00', '11:00:00', 'A101'),
('CS102', 'MONDAY', '13:00:00', '16:00:00', 'B202'),
('CS103', 'MONDAY', '18:00:00', '21:00:00', 'C303'),

-- Tuesday schedules
('CS104', 'TUESDAY', '08:00:00', '11:00:00', 'D404'),
('CS105', 'TUESDAY', '13:00:00', '16:00:00', 'E505'),
('CS101', 'TUESDAY', '18:00:00', '21:00:00', 'A101'),

-- Wednesday schedules
('CS102', 'WEDNESDAY', '08:00:00', '11:00:00', 'B202'),
('CS103', 'WEDNESDAY', '13:00:00', '16:00:00', 'C303'),
('CS104', 'WEDNESDAY', '18:00:00', '21:00:00', 'D404'),

-- Thursday schedules
('CS105', 'THURSDAY', '08:00:00', '11:00:00', 'E505'),
('CS101', 'THURSDAY', '13:00:00', '16:00:00', 'A101'),
('CS102', 'THURSDAY', '18:00:00', '21:00:00', 'B202'),

-- Friday schedules
('CS103', 'FRIDAY', '08:00:00', '11:00:00', 'C303'),
('CS104', 'FRIDAY', '13:00:00', '16:00:00', 'D404'),
('CS105', 'FRIDAY', '18:00:00', '21:00:00', 'E505'),

-- Saturday schedules (thêm một số lớp cuối tuần)
('CS101', 'SATURDAY', '08:00:00', '11:00:00', 'A101'),
('CS102', 'SATURDAY', '13:00:00', '16:00:00', 'B202'),
('CS103', 'SATURDAY', '18:00:00', '21:00:00', 'C303')
ON DUPLICATE KEY UPDATE
day_of_week = VALUES(day_of_week),
start_time = VALUES(start_time),
end_time = VALUES(end_time),
room = VALUES(room);

-- Insert attendance records for student 20520123
INSERT INTO attendance (id, course_id, student_id, session_date, status, note)
VALUES 
-- CS101 Attendance (15 sessions)
(1, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 14 DAY), 'PRESENT', 'On time'),
(2, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 13 DAY), 'PRESENT', 'On time'),
(3, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 12 DAY), 'LATE', 'Late 15 minutes'),
(4, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 11 DAY), 'PRESENT', 'On time'),
(5, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'ABSENT', 'Sick leave'),
(6, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 9 DAY), 'PRESENT', 'On time'),
(7, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 8 DAY), 'PRESENT', 'On time'),
(8, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'PRESENT', 'On time'),
(9, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 6 DAY), 'LATE', 'Late 5 minutes'),
(10, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'PRESENT', 'On time'),
(11, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'PRESENT', 'On time'),
(12, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'ABSENT', 'Family emergency'),
(13, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'PRESENT', 'On time'),
(14, 'CS101', '20520123', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'PRESENT', 'On time'),
(15, 'CS101', '20520123', CURDATE(), 'PRESENT', 'On time'),

-- CS102 Attendance (15 sessions)
(16, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 14 DAY), 'PRESENT', 'On time'),
(17, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 13 DAY), 'PRESENT', 'On time'),
(18, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 12 DAY), 'PRESENT', 'On time'),
(19, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 11 DAY), 'LATE', 'Late 10 minutes'),
(20, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'PRESENT', 'On time'),
(21, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 9 DAY), 'PRESENT', 'On time'),
(22, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 8 DAY), 'ABSENT', 'Sick leave'),
(23, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'PRESENT', 'On time'),
(24, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 6 DAY), 'PRESENT', 'On time'),
(25, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'PRESENT', 'On time'),
(26, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'LATE', 'Late 20 minutes'),
(27, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'PRESENT', 'On time'),
(28, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'PRESENT', 'On time'),
(29, 'CS102', '20520123', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'PRESENT', 'On time'),
(30, 'CS102', '20520123', CURDATE(), 'PRESENT', 'On time'),

-- CS103 Attendance (15 sessions)
(31, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 14 DAY), 'PRESENT', 'On time'),
(32, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 13 DAY), 'LATE', 'Late 5 minutes'),
(33, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 12 DAY), 'PRESENT', 'On time'),
(34, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 11 DAY), 'PRESENT', 'On time'),
(35, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'PRESENT', 'On time'),
(36, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 9 DAY), 'ABSENT', 'Sick leave'),
(37, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 8 DAY), 'PRESENT', 'On time'),
(38, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'PRESENT', 'On time'),
(39, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 6 DAY), 'PRESENT', 'On time'),
(40, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'LATE', 'Late 15 minutes'),
(41, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'PRESENT', 'On time'),
(42, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'PRESENT', 'On time'),
(43, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'ABSENT', 'Family emergency'),
(44, 'CS103', '20520123', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'PRESENT', 'On time'),
(45, 'CS103', '20520123', CURDATE(), 'PRESENT', 'On time')
ON DUPLICATE KEY UPDATE
course_id = VALUES(course_id),
student_id = VALUES(student_id),
session_date = VALUES(session_date),
status = VALUES(status),
note = VALUES(note);

-- Update courses with more details
UPDATE courses 
SET credits = 3, 
    subject_name = 'Introduction to Programming'
WHERE course_id = 'CS101';

UPDATE courses 
SET credits = 4, 
    subject_name = 'Data Structures'
WHERE course_id = 'CS102';

UPDATE courses 
SET credits = 4, 
    subject_name = 'Database Systems'
WHERE course_id = 'CS103';

-- Insert tuition payments for existing courses
INSERT INTO tuition_payments (student_id, course_id, status, payment_date, payment_method) VALUES
('20520123', 'CS101', 'PAID', '2024-03-15', 'BANK_TRANSFER'),    -- Introduction to Programming - Đã thanh toán
('20520123', 'CS102', 'UNPAID', NULL, NULL),                     -- Data Structures - Chưa thanh toán
('20520123', 'CS103', 'PARTIAL', '2024-03-10', 'CASH');          -- Database Systems - Thanh toán một phần

-- Check courses by semester
SELECT c.course_id, c.course_name, c.credits, c.semester 
FROM courses c 
WHERE c.semester = 'HK1_2024';

-- Check tuition payments
SELECT 
    c.course_id,
    c.course_name,
    c.credits,
    c.semester,
    tp.status,
    tp.payment_date,
    tp.payment_method,
    (c.credits * 1000000) as tuition_amount
FROM courses c
LEFT JOIN tuition_payments tp ON c.course_id = tp.course_id
WHERE c.semester = 'HK1_2024';

-- Calculate total tuition by semester
SELECT 
    c.semester,
    COUNT(c.course_id) as total_courses,
    SUM(c.credits) as total_credits,
    SUM(c.credits * 1000000) as total_tuition,
    SUM(CASE WHEN tp.status = 'PAID' THEN c.credits * 1000000 ELSE 0 END) as paid_amount,
    SUM(CASE WHEN tp.status = 'UNPAID' THEN c.credits * 1000000 ELSE 0 END) as unpaid_amount,
    SUM(CASE WHEN tp.status = 'PARTIAL' THEN c.credits * 1000000 ELSE 0 END) as partial_amount
FROM courses c
LEFT JOIN tuition_payments tp ON c.course_id = tp.course_id
GROUP BY c.semester;

-- Update courses with semester information
UPDATE courses 
SET semester = 'HK1_2024'
WHERE course_id IN ('CS101', 'CS102', 'CS103');

-- Update courses to only include 3 courses in HK1_2024
UPDATE courses 
SET semester = NULL
WHERE course_id IN ('CS104', 'CS105');

-- Update course enrollments to match
DELETE FROM course_student 
WHERE course_id IN ('CS104', 'CS105');

-- Update tuition payments to match the 3 courses
DELETE FROM tuition_payments 
WHERE course_id IN ('CS104', 'CS105');

-- Insert tuition payments for the 3 courses
INSERT INTO tuition_payments (student_id, course_id, status, payment_date, payment_method) VALUES
('20520123', 'CS101', 'PAID', '2024-03-15', 'BANK_TRANSFER'),    -- Introduction to Programming (3 credits) - Đã thanh toán
('20520123', 'CS102', 'UNPAID', NULL, NULL),                     -- Data Structures (4 credits) - Chưa thanh toán
('20520123', 'CS103', 'PARTIAL', '2024-03-10', 'CASH');          -- Database Systems (4 credits) - Thanh toán một phần

-- Verify total tuition calculation
SELECT 
    c.semester,
    COUNT(c.course_id) as total_courses,
    SUM(c.credits) as total_credits,
    SUM(c.credits * 1000000) as total_tuition,
    SUM(CASE WHEN tp.status = 'PAID' THEN c.credits * 1000000 ELSE 0 END) as paid_amount,
    SUM(CASE WHEN tp.status = 'UNPAID' THEN c.credits * 1000000 ELSE 0 END) as unpaid_amount,
    SUM(CASE WHEN tp.status = 'PARTIAL' THEN c.credits * 1000000 ELSE 0 END) as partial_amount
FROM courses c
LEFT JOIN tuition_payments tp ON c.course_id = tp.course_id
WHERE c.semester = 'HK1_2024'
GROUP BY c.semester;

-- Update semesters with correct data
UPDATE semesters 
SET total_credits = 11,  -- 3 + 4 + 4 credits from CS101, CS102, CS103
    completed_courses = 3,
    in_progress_courses = 0,
    gpa = 3.75
WHERE semester_id = 'HK1_2024';

-- Update academic progress to match
UPDATE academic_progress 
SET completed_credits = 11,  -- Current semester credits
    remaining_credits = 134, -- 145 - 11
    estimated_graduation = '2026-06'
WHERE student_id = '20520123';

-- Insert sample notifications for CS101
INSERT INTO notifications (title, content, created_at, course_id, type, status) VALUES
('Thông báo lịch học tuần tới', 'Lớp sẽ học về Arrays và Functions vào tuần tới. Vui lòng đọc trước tài liệu.', CURRENT_TIMESTAMP, 'CS101', 'ANNOUNCEMENT', 'ACTIVE'),
('Deadline Assignment 1', 'Hạn nộp Assignment 1 là 23:59 ngày 15/03/2024. Vui lòng nộp đúng hạn.', CURRENT_TIMESTAMP, 'CS101', 'DEADLINE', 'ACTIVE'),
('Kết quả Assignment 1', 'Kết quả Assignment 1 đã được cập nhật. Vui lòng kiểm tra trên hệ thống.', CURRENT_TIMESTAMP, 'CS101', 'GRADE', 'ACTIVE');

-- Mark notifications as read by student 20520123
INSERT INTO notification_read_status (notification_id, student_id) VALUES
(1, '20520123'), -- Đã đọc thông báo lịch học tuần tới
(2, '20520123'); -- Đã đọc deadline Assignment 1

-- Insert future attendance records for student 20520123
INSERT INTO attendance (id, course_id, student_id, session_date, status, note)
VALUES 
-- CS101 Future Sessions
(46, 'CS101', '20520123', '2025-06-08', 'PENDING', 'Future session'),
(47, 'CS101', '20520123', '2025-06-09', 'PENDING', 'Future session'),
(48, 'CS101', '20520123', '2025-06-10', 'PENDING', 'Future session'),
(49, 'CS101', '20520123', '2025-06-11', 'PENDING', 'Future session'),
(50, 'CS101', '20520123', '2025-06-12', 'PENDING', 'Future session'),

-- CS102 Future Sessions
(51, 'CS102', '20520123', '2025-06-08', 'PENDING', 'Future session'),
(52, 'CS102', '20520123', '2025-06-09', 'PENDING', 'Future session'),
(53, 'CS102', '20520123', '2025-06-10', 'PENDING', 'Future session'),
(54, 'CS102', '20520123', '2025-06-11', 'PENDING', 'Future session'),
(55, 'CS102', '20520123', '2025-06-12', 'PENDING', 'Future session'),

-- CS103 Future Sessions
(56, 'CS103', '20520123', '2025-06-08', 'PENDING', 'Future session'),
(57, 'CS103', '20520123', '2025-06-09', 'PENDING', 'Future session'),
(58, 'CS103', '20520123', '2025-06-10', 'PENDING', 'Future session'),
(59, 'CS103', '20520123', '2025-06-11', 'PENDING', 'Future session'),
(60, 'CS103', '20520123', '2025-06-12', 'PENDING', 'Future session')
ON DUPLICATE KEY UPDATE
course_id = VALUES(course_id),
student_id = VALUES(student_id),
session_date = VALUES(session_date),
status = VALUES(status),
note = VALUES(note);