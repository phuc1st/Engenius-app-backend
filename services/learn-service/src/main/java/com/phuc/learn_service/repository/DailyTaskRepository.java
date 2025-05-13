package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.DailyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyTaskRepository extends JpaRepository<DailyTask, Long> {
    List<DailyTask> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(dt) FROM DailyTask dt WHERE dt.userId = :userId AND dt.isCompleted = true AND dt.createdAt BETWEEN :start AND :end")
    int countCompletedTasksByUserIdAndDate(@Param("userId") String userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT SUM(dt.experiencePoints) FROM DailyTask dt WHERE dt.userId = :userId AND dt.isCompleted = true AND dt.createdAt BETWEEN :start AND :end")
    Integer sumExperiencePointsByUserIdAndDate(@Param("userId") String userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
