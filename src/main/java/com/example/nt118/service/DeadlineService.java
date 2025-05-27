package com.example.nt118.service;

import com.example.nt118.model.Deadline;
import com.example.nt118.repository.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeadlineService {
    @Autowired
    private DeadlineRepository deadlineRepository;

    public List<Deadline> getDeadlinesByCourseId(String courseId) {
        return deadlineRepository.findByCourseId(courseId);
    }
} 