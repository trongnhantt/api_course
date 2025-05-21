package com.example.nt118.service;

import com.example.nt118.model.LoginRequest;
import com.example.nt118.model.LoginResponse;
import com.example.nt118.model.Student;
import com.example.nt118.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Value("${admin.init-password}")
    private String adminPassword;
    
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        
        // Kiểm tra đăng nhập admin
        if ("admin".equals(request.getStudentId()) && 
            adminPassword.equals(request.getPassword())) {
            response.setSuccess(true);
            response.setMessage("Đăng nhập thành công");
            response.setToken("admin-token");
            return response;
        }
        
        // Kiểm tra đăng nhập sinh viên từ database
        Student student = studentRepository.findByStudentIdAndPassword(
            request.getStudentId(), 
            request.getPassword()
        );
        
        if (student != null) {
            response.setSuccess(true);
            response.setMessage("Đăng nhập thành công");
            response.setToken("student-token");
            response.setStudent(student);
            return response;
        }
        
        // Đăng nhập thất bại
        response.setSuccess(false);
        response.setMessage("Tên đăng nhập hoặc mật khẩu không đúng");
        return response;
    }
}