package com.example.nt118.api;

import com.example.nt118.model.Course;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseApi {
    @GET("api/courses")
    Call<List<Course>> getAllCourses();
    
    @GET("api/courses/{id}")
    Call<Course> getCourseById(@Path("id") Long id);
    
    @GET("api/courses/student/{studentId}")
    Call<List<Course>> getCoursesByStudentId(@Path("studentId") String studentId);
} 