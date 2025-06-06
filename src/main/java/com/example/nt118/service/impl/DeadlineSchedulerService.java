package com.example.nt118.service.impl;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.repository.AssignmentDeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DeadlineSchedulerService {

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    @Transactional
    public void updateDeadlineStatusAndPriority() {
        LocalDateTime now = LocalDateTime.now();
        List<AssignmentDeadline> deadlines = deadlineRepository.findAll();

        for (AssignmentDeadline deadline : deadlines) {
            // Update status based on deadline date
            if (deadline.getDeadlineDate().isBefore(now)) {
                if (deadline.getStatus() != AssignmentDeadline.DeadlineStatus.COMPLETED) {
                    deadline.setStatus(AssignmentDeadline.DeadlineStatus.OVERDUE);
                }
            }

            // Update priority based on time remaining
            long daysUntilDeadline = ChronoUnit.DAYS.between(now, deadline.getDeadlineDate());
            long hoursUntilDeadline = ChronoUnit.HOURS.between(now, deadline.getDeadlineDate());

            if (deadline.getStatus() == AssignmentDeadline.DeadlineStatus.PENDING) {
                if (daysUntilDeadline <= 1) { // Less than 24 hours
                    deadline.setPriority(AssignmentDeadline.DeadlinePriority.HIGH);
                } else if (daysUntilDeadline <= 3) { // Less than 72 hours
                    deadline.setPriority(AssignmentDeadline.DeadlinePriority.MEDIUM);
                } else {
                    deadline.setPriority(AssignmentDeadline.DeadlinePriority.LOW);
                }

                // Special case for very urgent deadlines (less than 6 hours)
                if (hoursUntilDeadline <= 6) {
                    deadline.setPriority(AssignmentDeadline.DeadlinePriority.URGENT);
                }
            }

            deadline.setUpdatedAt(now);
        }

        deadlineRepository.saveAll(deadlines);
    }

    // Run once at startup to ensure all deadlines are up-to-date
    @Transactional
    public void initializeDeadlineStatuses() {
        updateDeadlineStatusAndPriority();
    }
} 