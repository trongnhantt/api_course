package com.example.course.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionEnum {
    INSERT("INSERT"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    BANNED("BANNED"),
    ;

    private final String permissionName;
}
