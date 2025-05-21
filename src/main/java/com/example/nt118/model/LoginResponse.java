package com.example.nt118.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String message;
    private boolean success;
    private Student student; // Thêm thông tin sinh viên nếu đăng nhập thành công
}