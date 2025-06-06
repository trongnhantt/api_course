package com.example.nt118.repository;

import com.example.nt118.model.DeadlineAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeadlineAttachmentRepository extends JpaRepository<DeadlineAttachment, Long> {
} 