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
public class LoginByEmailRequest {
    @Email
    @Nonnull
    @Size(min = 1, max = 50, message = "EMAIL_INVALID")
    private String email;

    @Nonnull
    private String password;
}
