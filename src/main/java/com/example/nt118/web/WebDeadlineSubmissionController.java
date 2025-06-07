package com.example.nt118.web;

import com.example.nt118.model.AssignmentDeadline;
import com.example.nt118.model.DeadlineSubmission;
import com.example.nt118.service.AssignmentDeadlineService;
import com.example.nt118.service.DeadlineSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher/deadlines")
public class WebDeadlineSubmissionController {

    @Autowired
    private AssignmentDeadlineService deadlineService;

    @Autowired
    private DeadlineSubmissionService submissionService;

    @GetMapping("/{deadlineId}/submissions")
    public String listSubmissions(@PathVariable Long deadlineId, Model model) {
        AssignmentDeadline deadline = deadlineService.getDeadlineById(deadlineId);
        List<DeadlineSubmission> submissions = submissionService.getSubmissionsByDeadlineId(deadlineId);
        
        long totalStudents = submissions.size();
        long submittedCount = submissions.stream()
                .filter(s -> s.getStatus() == DeadlineSubmission.SubmissionStatus.SUBMITTED 
                        || s.getStatus() == DeadlineSubmission.SubmissionStatus.GRADED)
                .count();
        long gradedCount = submissions.stream()
                .filter(s -> s.getStatus() == DeadlineSubmission.SubmissionStatus.GRADED)
                .count();
        long notSubmittedCount = totalStudents - submittedCount;

        model.addAttribute("deadline", deadline);
        model.addAttribute("submissions", submissions);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("submittedCount", submittedCount);
        model.addAttribute("gradedCount", gradedCount);
        model.addAttribute("notSubmittedCount", notSubmittedCount);

        return "teacher/deadlines/submissions";
    }
} 