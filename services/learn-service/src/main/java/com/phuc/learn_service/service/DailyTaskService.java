package com.phuc.learn_service.service;

import com.phuc.learn_service.entity.DailyTask;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.repository.DailyTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyTaskService {
    private final DailyTaskRepository dailyTaskRepository;

    public List<DailyTask> getDailyTasks(String userId) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return dailyTaskRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
    }

    @Transactional
    public DailyTask completeTask(Long taskId, String userId) {
        DailyTask task = dailyTaskRepository.findById(taskId)
            .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        if (!task.getUserId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (task.getIsCompleted()) {
            throw new AppException(ErrorCode.TASK_ALREADY_COMPLETED);
        }

        task.setIsCompleted(true);
        task.setCompletedAt(LocalDateTime.now());

        return dailyTaskRepository.save(task);
    }

    public DailyTaskProgress getTaskProgress(String userId) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        int completedTasks = dailyTaskRepository.countCompletedTasksByUserIdAndDate(
            userId, startOfDay, endOfDay);
        Integer experiencePoints = dailyTaskRepository.sumExperiencePointsByUserIdAndDate(
            userId, startOfDay, endOfDay);

        return new DailyTaskProgress(
            completedTasks,
            experiencePoints != null ? experiencePoints : 0
        );
    }

    public record DailyTaskProgress(int completedTasks, int experiencePoints) {}
} 