package com.example.course.repository;

import com.example.course.entity.Folder;
import com.example.course.entity.HistoryView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryViewRepository extends JpaRepository<HistoryView, Long> {
    @Query("SELECT hv FROM HistoryView hv WHERE hv.user.userId = :userId ORDER BY hv.historyId ASC")
    List<HistoryView> getHistoryViewsByUserId(@Param("userId") Long userId, Pageable pageable);

}
