package com.example.course.service;

import com.example.course.dto.response.LoginResponse;
import com.example.course.entity.Administrator;
import com.example.course.entity.Role;
import com.example.course.entity.RoleUser;
import com.example.course.entity.User;
import com.example.course.entity.composite.RoleUserId;
import com.example.course.mapper.UserMapper;
import com.example.course.repository.AdministratorRepository;
import com.example.course.repository.RoleRepository;
import com.example.course.repository.RoleUserRepository;
import com.example.course.repository.UserRepository;
import com.example.course.dto.request.LoginByEmailRequest;
import com.example.course.dto.request.LoginByPhoneRequest;
import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.response.RegisterResponse;
import com.example.course.exception.AppRuntimeException;
import com.example.course.util.constant.ExceptionType;
import com.example.course.util.JwtProvider;
import com.example.course.util.StringHandler;
import com.nimbusds.jose.JOSEException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.course.dto.request.LoginByEmailRequest;
import com.example.course.dto.request.LoginByPhoneRequest;
import com.example.course.dto.request.RegisterRequest;
import com.example.course.dto.request.UpdateAdminRequest;
import com.example.course.dto.response.LoginResponse;
import com.example.course.dto.response.RegisterResponse;
import com.example.course.entity.Role;
import com.example.course.entity.RoleUser;
import com.example.course.entity.User;
import com.example.course.entity.composite.RoleUserId;
import com.example.course.exception.AppRuntimeException;
import com.example.course.mapper.UserMapper;
import com.example.course.repository.RoleRepository;
import com.example.course.repository.RoleUserRepository;
import com.example.course.repository.UserRepository;
import com.example.course.util.JwtProvider;
import com.example.course.util.StringHandler;
import com.example.course.util.constant.ExceptionType;
import com.nimbusds.jose.JOSEException;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Value("${admin.init-password}")
    private String adminInitPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;
    @Autowired
    private AdministratorRepository administratorRepository;

    public RegisterResponse register(RegisterRequest registerRequest) {
        areEmailAndPhoneEmpty(registerRequest);
        isUserExisted(registerRequest);

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(hashedPassword);

        User user = new User();
        userMapper.mapRegisterRequestToUser(user, registerRequest);
        user = userRepository.save(user);

        RegisterResponse registerResponse = new RegisterResponse();
        userMapper.mapUserToRegisterResponse(registerResponse, user);
        return registerResponse;
    }

    public LoginResponse loginByEmail(LoginByEmailRequest loginByEmailRequest) {
        User user = null;
        if (!StringHandler.isNullOrEmptyOrBlank(loginByEmailRequest.getEmail())) {
            user = userRepository.findByEmail(loginByEmailRequest.getEmail())
                    .orElseThrow(() -> new AppRuntimeException(ExceptionType.LOGIN_FAILED));
        }

        assert user != null;
        LoginResponse loginResponse = new LoginResponse();
        if (passwordEncoder.matches(loginByEmailRequest.getPassword(), user.getPassword())) {
            userMapper.mapUserToLoginResponse(loginResponse, user);
            loginResponse.setRole(user.getRoles().getFirst().getRole().getRoleName());
            try {
                loginResponse.setToken(JwtProvider.generateToken(user));
            } catch (JOSEException exception) {
                throw new AppRuntimeException(ExceptionType.TOKEN_SIGNING_FAILED);
            }
        } else {
            throw new AppRuntimeException(ExceptionType.LOGIN_FAILED);
        }

        return loginResponse;
    }

    public LoginResponse loginByPhone(LoginByPhoneRequest loginByPhoneRequest) {
        User user = null;
        if (!StringHandler.isNullOrEmptyOrBlank(loginByPhoneRequest.getPhone())) {
            user = userRepository.findByEmail(loginByPhoneRequest.getPhone())
                    .orElseThrow(() -> new AppRuntimeException(ExceptionType.LOGIN_FAILED));
        }

        assert user != null;
        LoginResponse loginResponse = new LoginResponse();
        if (passwordEncoder.matches(loginByPhoneRequest.getPassword(), user.getPassword())) {
            userMapper.mapUserToLoginResponse(loginResponse, user);
            String roles = String.join(" ",
                    user.getRoles().stream().map((role) -> role.getRole().getRoleName()).toList());
            loginResponse.setRole(roles);
            try {
                loginResponse.setToken(JwtProvider.generateToken(user));
            } catch (JOSEException exception) {
                throw new AppRuntimeException(ExceptionType.TOKEN_SIGNING_FAILED);
            }
        } else {
            throw new AppRuntimeException(ExceptionType.LOGIN_FAILED);
        }

        return loginResponse;
    }

    public void isUserExisted(RegisterRequest user) {
        if (!StringHandler.isNullOrEmptyOrBlank(user.getEmail())
                && StringHandler.isNullOrEmptyOrBlank(user.getPhone())) {
            userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new AppRuntimeException(ExceptionType.EMAIL_EXISTED));
            return;
        }
        if (StringHandler.isNullOrEmptyOrBlank(user.getEmail())
                && !StringHandler.isNullOrEmptyOrBlank(user.getPhone())) {
            userRepository.findByPhone(user.getPhone())
                    .orElseThrow(() -> new AppRuntimeException(ExceptionType.PHONE_EXISTED));
            return;
        }
        if (!StringHandler.isNullOrEmptyOrBlank(user.getEmail())
                && !StringHandler.isNullOrEmptyOrBlank(user.getPhone())) {
            boolean isEmailExisted = userRepository.findByEmail(user.getEmail()).isPresent();
            boolean isPhoneExisted = userRepository.findByPhone(user.getPhone()).isPresent();

            if (isEmailExisted && !isPhoneExisted) {
                throw new AppRuntimeException(ExceptionType.EMAIL_EXISTED);
            }
            if (!isEmailExisted && isPhoneExisted) {
                throw new AppRuntimeException(ExceptionType.PHONE_EXISTED);
            }
            if (isEmailExisted && isPhoneExisted) {
                throw new AppRuntimeException(ExceptionType.EMAIL_AND_PHONE_EXISTED);
            }
        }
    }

    public User getUserById(Long userId) {
        // return userRepository.findByUserId(userId).orElseThrow(() -> new
        // AppRuntimeException(ExceptionType.NOT_FOUND));
        return userRepository.findByUserId(userId);
    }

    public Page<User> getAdminList(Pageable p) {
        Page<User> res = userRepository.findAllAdmins(p);
        return res;
    }

    public RegisterResponse createAdmin(RegisterRequest registerRequest) {
        areEmailAndPhoneEmpty(registerRequest);
        isUserExisted(registerRequest);

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        registerRequest.setPassword(hashedPassword);

        User user = new User();
        userMapper.mapRegisterRequestToUser(user, registerRequest);
        user = userRepository.save(user);
        Role adminRole = roleRepository.findByRoleName("ADMIN");

        if (adminRole == null) {
            adminRole = Role.builder()
                    .roleName("ADMIN")
                    .build();
            roleRepository.save(adminRole);
        }

        boolean hasAdminRole = roleUserRepository.existsByUserAndRole(user, adminRole);

        System.out.println(user.getUserId() + " " + adminRole.getRoleId());

        if (!hasAdminRole) {
            RoleUserId roleUserId = new RoleUserId(adminRole.getRoleId(), user.getUserId());
            RoleUser roleUser = RoleUser.builder()
                    .id(roleUserId)
                    .user(user)
                    .role(adminRole)
                    .build();
            roleUser = roleUserRepository.save(roleUser);

        }
        RegisterResponse registerResponse = new RegisterResponse();
        userMapper.mapUserToRegisterResponse(registerResponse, user);
        return registerResponse;
    }

    public void deleteUser(Long userId) {
        roleUserRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

    public Optional<User> updateAdmin(Long userId, UpdateAdminRequest updatedUser) {

        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            if (!updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            existingUser.setGender(updatedUser.getGender());
            return userRepository.save(existingUser);
        });
    }

    public void areEmailAndPhoneEmpty(RegisterRequest user) {
        if (StringHandler.isNullOrEmptyOrBlank(user.getEmail())
                && StringHandler.isNullOrEmptyOrBlank(user.getPhone())) {
            throw new AppRuntimeException(ExceptionType.EMAIL_AND_PHONE_EMPTY);
        }

    }

    @Transactional
    public void initAdmin() {
        System.out.println("Init admin");

        String userName = "admin";
        User user = userRepository.findByUsername(userName).orElse(null);

        if (user == null) {
            // Nếu user chưa tồn tại, tạo mới user
            Administrator administrator = new Administrator();
            administrator = administratorRepository.save(administrator);

            user = User.builder()
                    .username(userName)
                    .email("admin@example.com")
                    .password(passwordEncoder.encode(adminInitPassword))
                    .administrator(administrator)
                    .build();
            user = userRepository.save(user);
        } else
            return;

        Role adminRole = roleRepository.findByRoleName("ADMIN");

        if (adminRole == null) {
            adminRole = Role.builder()
                    .roleName("ADMIN")
                    .build();
            roleRepository.save(adminRole);
        }

        boolean hasAdminRole = roleUserRepository.existsByUserAndRole(user, adminRole);

        System.out.println(user.getUserId() + " " + adminRole.getRoleId());

        if (!hasAdminRole) {
            RoleUserId roleUserId = new RoleUserId(adminRole.getRoleId(), user.getUserId());
            RoleUser roleUser = RoleUser.builder()
                    .id(roleUserId)
                    .user(user)
                    .role(adminRole)
                    .build();
            System.out.println(roleUser.getId().getRoleId() + " 99 " + roleUser.getId().getUserId());
            roleUser = roleUserRepository.save(roleUser);
            System.out.println(roleUser.getRole().getRoleName() + " " + roleUser.getUser().getUserId());

        }
    }

}