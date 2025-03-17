package com.example.course.configuration;

import com.example.course.entity.Permission;
import com.example.course.entity.Role;
import com.example.course.repository.PermissionRepository;
import com.example.course.repository.RoleRepository;
import com.example.course.repository.UserRepository;
import com.example.course.service.UserService;
import com.example.course.util.constant.PermissionEnum;
import com.example.course.util.constant.RoleEnum;
import com.example.course.entity.Role;
import com.example.course.repository.PermissionRepository;
import com.example.course.repository.RoleRepository;
import com.example.course.service.UserService;
import com.example.course.util.constant.RoleEnum;
import com.example.course.entity.Permission;
import com.example.course.util.constant.PermissionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.course.entity.Permission;
import com.example.course.entity.Role;
import com.example.course.repository.PermissionRepository;
import com.example.course.repository.RoleRepository;
import com.example.course.repository.UserRepository;
import com.example.course.service.UserService;
import com.example.course.util.constant.PermissionEnum;
import com.example.course.util.constant.RoleEnum;

@Configuration
public class ApplicationInit {
    @Bean
    @Autowired
    ApplicationRunner applicationRunner(UserService userService, RoleRepository roleRepository,
            PermissionRepository permissionRepository) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                // Add roles if they don't exist
                for (RoleEnum roleEnum : RoleEnum.values()) {
                    if (!roleRepository.existsByRoleName(roleEnum.getRoleName())) {
                        roleRepository.save(Role.builder().roleName(roleEnum.getRoleName()).build());
                    }
                }

                // Add permissions if they don't exist
                for (PermissionEnum permissionEnum : PermissionEnum.values()) {
                    if (!permissionRepository.existsByPermissionName(permissionEnum.getPermissionName())) {
                        permissionRepository
                                .save(Permission.builder().permissionName(permissionEnum.getPermissionName()).build());
                    }
                }

                userService.initAdmin();
            }
        };
    }

}
