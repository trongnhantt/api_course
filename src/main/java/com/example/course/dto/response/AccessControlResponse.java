package com.example.course.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccessControlResponse {
    private Byte status;
    private String message;
    private String redirectUrl;
}
