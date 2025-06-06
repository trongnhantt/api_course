package com.example.nt118.service.impl;

import com.example.nt118.model.*;
import com.example.nt118.repository.AssignmentDeadlineRepository;
import com.example.nt118.repository.CourseRepository;
import com.example.nt118.repository.StudentRepository;
import com.example.nt118.service.DeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeadlineServiceImpl implements DeadlineService {

    @Autowired
    private AssignmentDeadlineRepository deadlineRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public DeadlineResponse getAllDeadlines(String courseId, String status, String type, String priority, int page, int size) {
        Page<AssignmentDeadline> deadlines = deadlineRepository.findAll(PageRequest.of(page, size));
        return createDeadlineResponse(deadlines.getContent());
    }

    @Override
    public DeadlineResponse getDeadlineById(Long id) {
        AssignmentDeadline deadline = deadlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));
        return createDeadlineResponse(List.of(deadline));
    }

    @Override
    @Transactional
    public DeadlineResponse createDeadline(AssignmentDeadline deadline) {
        deadline.setCreatedAt(LocalDateTime.now());
        deadline.setUpdatedAt(LocalDateTime.now());
        AssignmentDeadline savedDeadline = deadlineRepository.save(deadline);
        return createDeadlineResponse(List.of(savedDeadline));
    }

    @Override
    @Transactional
    public DeadlineResponse updateDeadline(Long id, AssignmentDeadline deadline) {
        AssignmentDeadline existingDeadline = deadlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deadline not found"));
        
        existingDeadline.setTitle(deadline.getTitle());
        existingDeadline.setDescription(deadline.getDescription());
        existingDeadline.setDeadlineDate(deadline.getDeadlineDate());
        existingDeadline.setSubmissionType(deadline.getSubmissionType());
        existingDeadline.setPriority(deadline.getPriority());
        existingDeadline.setMaxPoints(deadline.getMaxPoints());
        existingDeadline.setWeightPercentage(deadline.getWeightPercentage());
        existingDeadline.setIsGroupWork(deadline.getIsGroupWork());
        existingDeadline.setStatus(deadline.getStatus());
        existingDeadline.setUpdatedAt(LocalDateTime.now());

        AssignmentDeadline updatedDeadline = deadlineRepository.save(existingDeadline);
        return createDeadlineResponse(List.of(updatedDeadline));
    }

    @Override
    @Transactional
    public void deleteDeadline(Long id) {
        deadlineRepository.deleteById(id);
    }

    @Override
    public DeadlineResponse.DeadlineSummary getDeadlineSummary() {
        DeadlineResponse.DeadlineSummary summary = new DeadlineResponse.DeadlineSummary();
        summary.setTotalDeadlines((int) deadlineRepository.count());
        summary.setPendingDeadlines(deadlineRepository.countByStatus(AssignmentDeadline.DeadlineStatus.PENDING));
        summary.setCompletedDeadlines(deadlineRepository.countByStatus(AssignmentDeadline.DeadlineStatus.COMPLETED));
        summary.setOverdueDeadlines(deadlineRepository.countByStatus(AssignmentDeadline.DeadlineStatus.OVERDUE));
        summary.setUpcomingWeek(deadlineRepository.countUpcomingWeek());
        return summary;
    }

    @Override
    public DeadlineResponse.DeadlineFilters getDeadlineFilters() {
        DeadlineResponse.DeadlineFilters filters = new DeadlineResponse.DeadlineFilters();
        
        List<DeadlineResponse.CourseInfo> courses = courseRepository.findAll().stream()
                .map(this::mapToCourseInfo)
                .collect(Collectors.toList());
        
        List<String> types = List.of("ASSIGNMENT", "EXAM", "PROJECT", "HOMEWORK", "PRESENTATION");
        List<String> statuses = List.of("PENDING", "COMPLETED", "OVERDUE");
        
        filters.setCourses(courses);
        filters.setTypes(types);
        filters.setStatuses(statuses);
        
        return filters;
    }

    @Override
    public DeadlineResponse getCourseDeadlines(String courseId, String status) {
        List<AssignmentDeadline> deadlines = deadlineRepository.findByCourseId(courseId);
        return createDeadlineResponse(deadlines);
    }

    @Override
    public StudentDeadlineResponse getStudentDeadlines(String studentId) {
        List<AssignmentDeadline> studentDeadlines = deadlineRepository.findByStudentId(studentId);
        
        // Update deadline statuses based on current time
        LocalDateTime now = LocalDateTime.now();
        studentDeadlines.forEach(deadline -> {
            if (deadline.getStatus() != AssignmentDeadline.DeadlineStatus.COMPLETED) {
                if (deadline.getDeadlineDate().isBefore(now)) {
                    deadline.setStatus(AssignmentDeadline.DeadlineStatus.OVERDUE);
                } else {
                    deadline.setStatus(AssignmentDeadline.DeadlineStatus.PENDING);
                }
                deadlineRepository.save(deadline);
            }
        });
        
        // Get deadlines by status and order
        List<AssignmentDeadline> pendingDeadlines = studentDeadlines.stream()
                .filter(d -> d.getStatus() == AssignmentDeadline.DeadlineStatus.PENDING)
                .sorted((d1, d2) -> d1.getDeadlineDate().compareTo(d2.getDeadlineDate()))
                .toList();

        List<AssignmentDeadline> completedDeadlines = studentDeadlines.stream()
                .filter(d -> d.getStatus() == AssignmentDeadline.DeadlineStatus.COMPLETED)
                .sorted((d1, d2) -> d2.getDeadlineDate().compareTo(d1.getDeadlineDate()))
                .toList();

        List<AssignmentDeadline> overdueDeadlines = studentDeadlines.stream()
                .filter(d -> d.getStatus() == AssignmentDeadline.DeadlineStatus.OVERDUE)
                .sorted((d1, d2) -> d2.getDeadlineDate().compareTo(d1.getDeadlineDate()))
                .toList();

        // Combine all deadlines in the desired order
        List<AssignmentDeadline> orderedDeadlines = new ArrayList<>();
        orderedDeadlines.addAll(pendingDeadlines);
        orderedDeadlines.addAll(completedDeadlines);
        orderedDeadlines.addAll(overdueDeadlines);

        StudentDeadlineResponse response = new StudentDeadlineResponse();
        response.setStatus("success");
        response.setMessage("Deadlines retrieved successfully");
        response.setTotalDeadlines(orderedDeadlines.size());
        response.setPendingDeadlines(pendingDeadlines.size());
        response.setCompletedDeadlines(completedDeadlines.size());
        response.setOverdueDeadlines(overdueDeadlines.size());
        
        List<Map<String, Object>> deadlineList = new ArrayList<>();
        
        for (AssignmentDeadline deadline : orderedDeadlines) {
            Map<String, Object> deadlineMap = new HashMap<>();
            deadlineMap.put("id", deadline.getId());
            deadlineMap.put("title", deadline.getTitle());
            deadlineMap.put("description", deadline.getDescription());
            deadlineMap.put("deadlineDate", deadline.getDeadlineDate());
            deadlineMap.put("submissionType", deadline.getSubmissionType());
            deadlineMap.put("priority", deadline.getPriority());
            deadlineMap.put("status", deadline.getStatus());
            deadlineMap.put("maxPoints", deadline.getMaxPoints());
            deadlineMap.put("weightPercentage", deadline.getWeightPercentage());
            deadlineMap.put("isGroupWork", deadline.getIsGroupWork());
            deadlineMap.put("submissionUrl", deadline.getSubmissionUrl());
            
            // Add course information
            Map<String, Object> courseInfo = new HashMap<>();
            courseInfo.put("courseId", deadline.getCourse().getCourseId());
            courseInfo.put("courseName", deadline.getCourse().getCourseName());
            courseInfo.put("room", deadline.getCourse().getRoom());
            courseInfo.put("schedule", deadline.getCourse().getSchedule());
            deadlineMap.put("course", courseInfo);
            
            // Add teacher information
            Map<String, Object> teacherInfo = new HashMap<>();
            teacherInfo.put("teacherId", deadline.getCourse().getTeacher().getTeacherId());
            teacherInfo.put("name", deadline.getCourse().getTeacher().getName());
            teacherInfo.put("email", deadline.getCourse().getTeacher().getEmail());
            deadlineMap.put("teacher", teacherInfo);
            
            // Add attachment information
            List<Map<String, Object>> attachments = deadline.getAttachments().stream()
                    .map(attachment -> {
                        Map<String, Object> attachmentMap = new HashMap<>();
                        attachmentMap.put("fileName", attachment.getFileName());
                        attachmentMap.put("fileUrl", attachment.getFileUrl());
                        attachmentMap.put("fileType", attachment.getFileType());
                        return attachmentMap;
                    })
                    .collect(Collectors.toList());
            deadlineMap.put("attachments", attachments);
            
            deadlineList.add(deadlineMap);
        }
        
        response.setDeadlines(deadlineList);
        return response;
    }

    @Override
    public DeadlineListResponse getStudentCourseDeadlines(String studentId, String courseId) {
        // Get all deadlines for the course
        List<AssignmentDeadline> deadlines = deadlineRepository.findByCourseId(courseId);
        
        // Create response
        DeadlineListResponse response = new DeadlineListResponse();
        response.setStatus("success");
        
        // Map deadlines to response format
        List<DeadlineListResponse.DeadlineItem> deadlineItems = deadlines.stream()
            .map(deadline -> {
                DeadlineListResponse.DeadlineItem item = new DeadlineListResponse.DeadlineItem();
                item.setId(deadline.getId().toString());
                item.setTitle(deadline.getTitle());
                item.setDescription(deadline.getDescription());
                item.setDueDate(deadline.getDeadlineDate());
                item.setCourseId(deadline.getCourse().getCourseId());
                item.setCourseName(deadline.getCourse().getCourseName());
                item.setType(deadline.getSubmissionType().name());
                item.setMaxGrade(deadline.getMaxPoints());
                
                // Map attachments
                item.setAttachments(deadline.getAttachments().stream()
                    .map(attachment -> {
                        DeadlineListResponse.Attachment att = new DeadlineListResponse.Attachment();
                        att.setId(attachment.getId().toString());
                        att.setName(attachment.getFileName());
                        att.setUrl(attachment.getFileUrl());
                        att.setType(attachment.getFileType());
                        return att;
                    })
                    .collect(Collectors.toList()));
                
                // Get submission for this student if exists
                Optional<DeadlineSubmission> submissionOpt = deadline.getSubmissions().stream()
                    .filter(s -> s.getStudent().getStudentId().equals(studentId))
                    .findFirst();
                
                if (submissionOpt.isPresent()) {
                    DeadlineSubmission submission = submissionOpt.get();
                    item.setStatus(submission.getStatus().name());
                    item.setSubmittedAt(submission.getSubmitDate());
                    item.setGrade(submission.getGrade());
                    item.setSubmissionId(submission.getId());
                } else {
                    item.setStatus("PENDING");
                    item.setSubmittedAt(null);
                    item.setGrade(null);
                    item.setSubmissionId(null);
                }
                
                // Map requirements (placeholder for now)
                item.setRequirements(new ArrayList<>());
                
                return item;
            })
            .collect(Collectors.toList());
        
        response.setData(deadlineItems);
        return response;
    }

    private DeadlineResponse createDeadlineResponse(List<AssignmentDeadline> deadlines) {
        DeadlineResponse response = new DeadlineResponse();
        response.setStatus("success");
        
        DeadlineResponse.DeadlineData data = new DeadlineResponse.DeadlineData();
        data.setDeadlines(deadlines.stream()
                .map(this::mapToDeadlineItem)
                .collect(Collectors.toList()));
        data.setSummary(getDeadlineSummary());
        data.setFilters(getDeadlineFilters());
        
        response.setData(data);
        return response;
    }

    private DeadlineResponse.DeadlineItem mapToDeadlineItem(AssignmentDeadline deadline) {
        DeadlineResponse.DeadlineItem item = new DeadlineResponse.DeadlineItem();
        item.setId(deadline.getId());
        item.setTitle(deadline.getTitle());
        item.setDescription(deadline.getDescription());
        item.setCourse(mapToCourseInfo(deadline.getCourse()));
        item.setDueDate(deadline.getDeadlineDate());
        item.setStatus(deadline.getStatus().name().toLowerCase());
        item.setPriority(deadline.getPriority().name().toLowerCase());
        item.setType(deadline.getSubmissionType().name().toLowerCase());
        item.setAttachments(mapToAttachmentInfo(deadline.getAttachments()));
        item.setSubmission(mapToSubmissionInfo(deadline.getSubmissions()));
        item.setReminders(mapToReminderInfo(deadline.getReminders()));
        item.setCreatedAt(deadline.getCreatedAt());
        item.setUpdatedAt(deadline.getUpdatedAt());
        return item;
    }

    private DeadlineResponse.CourseInfo mapToCourseInfo(Course course) {
        DeadlineResponse.CourseInfo courseInfo = new DeadlineResponse.CourseInfo();
        courseInfo.setCourseId(course.getCourseId());
        courseInfo.setCourseName(course.getCourseName());
        courseInfo.setLecturer(mapToLecturerInfo(course.getTeacher()));
        return courseInfo;
    }

    private DeadlineResponse.LecturerInfo mapToLecturerInfo(Teacher teacher) {
        DeadlineResponse.LecturerInfo lecturerInfo = new DeadlineResponse.LecturerInfo();
        lecturerInfo.setId(teacher.getTeacherId());
        lecturerInfo.setName(teacher.getName());
        lecturerInfo.setEmail(teacher.getEmail());
        return lecturerInfo;
    }

    private List<DeadlineResponse.AttachmentInfo> mapToAttachmentInfo(Set<DeadlineAttachment> attachments) {
        return attachments.stream()
                .map(attachment -> {
                    DeadlineResponse.AttachmentInfo info = new DeadlineResponse.AttachmentInfo();
                    info.setId(attachment.getId());
                    info.setFileName(attachment.getFileName());
                    info.setFileUrl(attachment.getFileUrl());
                    info.setFileType(attachment.getFileType());
                    info.setFileSize(attachment.getFileSize());
                    return info;
                })
                .collect(Collectors.toList());
    }

    private DeadlineResponse.SubmissionInfo mapToSubmissionInfo(Set<DeadlineSubmission> submissions) {
        if (submissions.isEmpty()) {
            DeadlineResponse.SubmissionInfo info = new DeadlineResponse.SubmissionInfo();
            info.setStatus("not_submitted");
            return info;
        }

        DeadlineSubmission submission = submissions.iterator().next();
        DeadlineResponse.SubmissionInfo info = new DeadlineResponse.SubmissionInfo();
        info.setStatus(submission.getStatus().name().toLowerCase());
        info.setSubmitDate(submission.getSubmitDate());
        info.setGrade(submission.getGrade());
        info.setFeedback(submission.getFeedback());
        info.setSubmittedFiles(mapToSubmittedFileInfo(submission.getSubmittedFiles()));
        return info;
    }

    private List<DeadlineResponse.SubmittedFileInfo> mapToSubmittedFileInfo(Set<SubmissionFile> files) {
        return files.stream()
                .map(file -> {
                    DeadlineResponse.SubmittedFileInfo info = new DeadlineResponse.SubmittedFileInfo();
                    info.setId(file.getId());
                    info.setFileName(file.getFileName());
                    info.setFileUrl(file.getFileUrl());
                    info.setFileType(file.getFileType());
                    info.setFileSize(file.getFileSize());
                    return info;
                })
                .collect(Collectors.toList());
    }

    private List<DeadlineResponse.ReminderInfo> mapToReminderInfo(Set<DeadlineReminder> reminders) {
        return reminders.stream()
                .map(reminder -> {
                    DeadlineResponse.ReminderInfo info = new DeadlineResponse.ReminderInfo();
                    info.setId(reminder.getId());
                    info.setReminderTime(reminder.getReminderTime());
                    info.setIsEnabled(reminder.getIsEnabled());
                    return info;
                })
                .collect(Collectors.toList());
    }

    private StudentDeadlineResponse.DeadlineItem mapToStudentDeadlineItem(AssignmentDeadline deadline) {
        StudentDeadlineResponse.DeadlineItem item = new StudentDeadlineResponse.DeadlineItem();
        item.setId(deadline.getId().toString());
        item.setSubject(mapToSubjectInfo(deadline.getCourse()));
        item.setName(deadline.getTitle());
        item.setDescription(deadline.getDescription());
        item.setDeadline(deadline.getDeadlineDate());
        item.setStatus(deadline.getStatus().name().toLowerCase());
        item.setPriority(deadline.getPriority().name().toLowerCase());
        item.setAttachments(mapToSimpleAttachmentInfo(deadline.getAttachments()));
        return item;
    }

    private StudentDeadlineResponse.SubjectInfo mapToSubjectInfo(Course course) {
        StudentDeadlineResponse.SubjectInfo subject = new StudentDeadlineResponse.SubjectInfo();
        subject.setCode(course.getCourseId());
        subject.setName(course.getCourseName());
        subject.setLecturer(mapToSimpleLecturerInfo(course.getTeacher()));
        return subject;
    }

    private StudentDeadlineResponse.LecturerInfo mapToSimpleLecturerInfo(Teacher teacher) {
        StudentDeadlineResponse.LecturerInfo lecturer = new StudentDeadlineResponse.LecturerInfo();
        lecturer.setId(teacher.getTeacherId());
        lecturer.setName(teacher.getName());
        lecturer.setEmail(teacher.getEmail());
        return lecturer;
    }

    private List<StudentDeadlineResponse.AttachmentInfo> mapToSimpleAttachmentInfo(Set<DeadlineAttachment> attachments) {
        return attachments.stream()
                .map(attachment -> {
                    StudentDeadlineResponse.AttachmentInfo info = new StudentDeadlineResponse.AttachmentInfo();
                    info.setFileName(attachment.getFileName());
                    info.setFileUrl(attachment.getFileUrl());
                    return info;
                })
                .collect(Collectors.toList());
    }

    private StudentDeadlineResponse.DeadlineSummary getStudentDeadlineSummary(List<AssignmentDeadline> deadlines) {
        StudentDeadlineResponse.DeadlineSummary summary = new StudentDeadlineResponse.DeadlineSummary();
        summary.setTotalDeadlines(deadlines.size());
        summary.setPendingDeadlines((int) deadlines.stream()
                .filter(d -> d.getStatus() == AssignmentDeadline.DeadlineStatus.PENDING)
                .count());
        summary.setCompletedDeadlines((int) deadlines.stream()
                .filter(d -> d.getStatus() == AssignmentDeadline.DeadlineStatus.COMPLETED)
                .count());
        summary.setOverdueDeadlines((int) deadlines.stream()
                .filter(d -> d.getStatus() == AssignmentDeadline.DeadlineStatus.OVERDUE)
                .count());
        return summary;
    }
} 