package com.example.nt118.service;

import com.example.nt118.model.LoginRequest;
import com.example.nt118.model.LoginResponse;
import com.example.nt118.model.Student;
import com.example.nt118.repository.StudentRepository;
import com.example.nt118.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Value("${admin.init-password}")
    private String adminPassword;
    
    public LoginResponse login(LoginRequest request) {
        // Kiểm tra đăng nhập admin
        if ("admin".equals(request.getStudentId()) && 
            adminPassword.equals(request.getPassword())) {
            String token = jwtTokenProvider.generateToken("admin");
            return new LoginResponse(
                token,
                "admin",
                "Admin",
                "admin@example.com",
                "admin",
                "Đăng nhập thành công",
                true
            );
        }
        
        // Kiểm tra đăng nhập sinh viên
        Optional<Student> studentOpt = studentRepository.findByStudentIdAndPassword(
            request.getStudentId(), 
            request.getPassword()
        );
        
        if (studentOpt.isEmpty()) {
            return new LoginResponse(
                null,
                null,
                null,
                null,
                null,
                "Tên đăng nhập hoặc mật khẩu không đúng",
                false
            );
        }
        
        Student student = studentOpt.get();
        String token = jwtTokenProvider.generateToken(student.getStudentId());
        
        return new LoginResponse(
            token,
            student.getStudentId(),
            student.getName(),
            student.getEmail(),
            "student",
            "Đăng nhập thành công",
            true
        );
    }
}