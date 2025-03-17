package com.example.course.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    LECTURER("LECTURER"),
    GUEST("GUEST");

    private final String roleName;
}