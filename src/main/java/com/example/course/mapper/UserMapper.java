package com.example.course.mapper;

import com.example.course.dto.response.LoginResponse;
import com.example.course.entity.User;
import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.response.LoginResponse;
import com.example.course.dto.response.RegisterResponse;
import com.example.course.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public void mapRegisterRequestToUser(@MappingTarget User user, RegisterRequest request);

    public void mapUserToRegisterResponse(@MappingTarget RegisterResponse response, User user);

    public void mapUserToLoginResponse(@MappingTarget LoginResponse response, User user);
}
