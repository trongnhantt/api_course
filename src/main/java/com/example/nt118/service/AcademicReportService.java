package com.example.nt118.service;

import com.example.nt118.model.*;
import com.example.nt118.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import com.example.nt118.model.AcademicReportResponse;

public interface AcademicReportService {
    AcademicReportResponse getAcademicReport(String semesterId, String studentId);
} 