-- Drop existing data
DELETE FROM students;
DELETE FROM courses;

-- Thêm ràng buộc unique cho student_id nếu chưa tồn tại
SET @constraint_exists = (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
    AND TABLE_NAME = 'students'
    AND CONSTRAINT_NAME = 'uk_student_id'
);

SET @sql = IF(@constraint_exists = 0,
    'ALTER TABLE students ADD CONSTRAINT uk_student_id UNIQUE (student_id)',
    'SELECT "Constraint uk_student_id already exists"'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Insert sample students
INSERT INTO students (student_id, name, email, password, dob, avatar_url, class_name) VALUES
('SV001', 'Nguyen Van A', 'nguyenvana@example.com', 'password123', '2000-01-01', 'https://example.com/avatar1.jpg', 'CNTT1'),
('SV002', 'Tran Thi B', 'tranthib@example.com', 'password123', '2000-02-02', 'https://example.com/avatar2.jpg', 'CNTT1'),
('SV003', 'Le Van C', 'levanc@example.com', 'password123', '2000-03-03', 'https://example.com/avatar3.jpg', 'CNTT2');

-- Insert sample courses
INSERT INTO courses (course_id, course_name, credits, semester, start_date, end_date, status, instructor, room, schedule)
VALUES 
('CS101', 'Introduction to Programming', 3, '2024-1', '2024-01-01', '2024-05-01', 'active', 'Dr. Smith', 'Room 101', 'Monday 8:00-10:00'),
('CS102', 'Data Structures', 4, '2024-1', '2024-01-01', '2024-05-01', 'active', 'Dr. Johnson', 'Room 102', 'Wednesday 13:00-15:00'); 