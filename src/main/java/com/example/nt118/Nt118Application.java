package com.example.nt118;

import com.example.nt118.service.impl.DeadlineSchedulerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Nt118Application {

    public static void main(String[] args) {
        SpringApplication.run(Nt118Application.class, args);
    }

    @Bean
    CommandLineRunner initDeadlines(DeadlineSchedulerService schedulerService) {
        return args -> {
            // Initialize deadline statuses at startup
            schedulerService.initializeDeadlineStatuses();
        };
    }
} 