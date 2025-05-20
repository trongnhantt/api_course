package com.example.nt118.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Course {
    @SerializedName("id")
    private Long id;
    
    @SerializedName("courseId")
    private String courseId;
    
    @SerializedName("courseName")
    private String courseName;
    
    @SerializedName("credits")
    private Integer credits;
    
    @SerializedName("semester")
    private String semester;
    
    @SerializedName("startDate")
    private Date startDate;
    
    @SerializedName("endDate")
    private Date endDate;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("instructor")
    private String instructor;
    
    @SerializedName("room")
    private String room;
    
    @SerializedName("schedule")
    private String schedule;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
} 