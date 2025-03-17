package com.example.course.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.request.UpdateAdminRequest;
import com.example.course.dto.response.AppResponse;
import com.example.course.dto.response.RegisterResponse;
import com.example.course.entity.User;
import com.example.course.service.UserService;
import com.example.course.util.ApiMessage;

import jakarta.validation.Valid;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("admin")
    public Page<User> getAdmins(@RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size) {
        return this.userService.getAdminList(PageRequest.of(page, size));
    }

    @PostMapping("admin")
    public ResponseEntity<AppResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest user) {
        RegisterResponse registerResponse = userService.createAdmin(user);
        return new ResponseEntity<AppResponse<RegisterResponse>>(
                new AppResponse<RegisterResponse>(HttpStatus.OK.value(),
                        ApiMessage.SUCCESS, registerResponse),
                HttpStatus.OK);
    }

    @PutMapping("admin/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "userId") Long userId,
            @RequestBody @Valid UpdateAdminRequest user) {
        Optional<User> updatedUser = userService.updateAdmin(userId, user);
        return updatedUser.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("admin/{userId}")
    public User getOne(@PathVariable(name = "userId") Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("admin/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
