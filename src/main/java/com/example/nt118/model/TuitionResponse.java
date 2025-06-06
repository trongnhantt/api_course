package com.example.nt118.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TuitionResponse {
    private String semester;
    private Long totalAmount;
    private Integer totalCredits;
    private List<CourseTuition> courses;
    private PaymentSummary paymentSummary;

    @Data
    public static class CourseTuition {
        private String classId;
        private Integer credits;
        private Long amount;
        private PaymentStatus status;
        private String subjectName;
        private LocalDate paymentDate;
        private PaymentMethod paymentMethod;
    }

    @Data
    public static class PaymentSummary {
        private Long paid;
        private Long unpaid;
        private Long partial;
    }

    public enum PaymentStatus {
        PAID,
        UNPAID,
        PARTIAL
    }

    public enum PaymentMethod {
        CASH,
        BANK_TRANSFER,
        CREDIT_CARD
    }
} 