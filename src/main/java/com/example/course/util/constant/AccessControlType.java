package com.example.course.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessControlType {
    OK((byte) 1, "OK"),
    DENIED((byte) 2, "DENIED"),
    REDIRECT((byte) 3, "REDIRECT"),
    NOT_EXIST((byte) 4, "NOT EXIST");

    private final Byte status;
    private final String message;
}
