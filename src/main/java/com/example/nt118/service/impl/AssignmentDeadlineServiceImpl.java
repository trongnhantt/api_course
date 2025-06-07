package com.example.nt118.service.impl;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.repository.AssignmentDeadlineRepository;
import com.example.nt118.service.AssignmentDeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentDeadlineServiceImpl implements AssignmentDeadlineService {

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Override
    public AssignmentDeadline getDeadlineById(Long id) {
        return deadlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));
    }
} 