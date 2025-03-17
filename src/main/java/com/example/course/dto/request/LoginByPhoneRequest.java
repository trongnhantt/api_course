package com.example.course.dto.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginByPhoneRequest {
    @Email
    @Nonnull
    @Size(min = 10, max = 10, message = "PHONE_INVALID")
    private String phone;

    @Nonnull
    private String password;
}
