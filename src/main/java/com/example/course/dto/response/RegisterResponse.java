package com.example.course.dto.response;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private Long userId;
    private String username;
    private String email;
    private String phone;
    private Boolean gender;
    private LocalDate dob;
    private String countryCode;
}
