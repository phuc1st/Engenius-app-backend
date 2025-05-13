package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.entity.DailyTask;
import com.phuc.learn_service.service.DailyTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily-tasks")
@RequiredArgsConstructor
public class DailyTaskController {
    private final DailyTaskService dailyTaskService;

    @GetMapping("/{userId}")
    public ApiResponse<List<DailyTask>> getDailyTasks(
        @PathVariable String userId
    ) {
        return ApiResponse.<List<DailyTask>>builder()
                .result(dailyTaskService.getDailyTasks(userId))
                .build();
    }

    @PostMapping("/{taskId}/complete/{userId}")
    public ApiResponse<DailyTask> completeTask(
        @PathVariable Long taskId,
        @PathVariable String userId
    ) {
        return ApiResponse.<DailyTask>builder()
                .result(dailyTaskService.completeTask(taskId, userId))
                .build();
    }

    @GetMapping("/progress/{userId}")
    public ApiResponse<DailyTaskService.DailyTaskProgress> getTaskProgress(
        @PathVariable String userId
    ) {
        return ApiResponse.<DailyTaskService.DailyTaskProgress>builder()
                .result(dailyTaskService.getTaskProgress(userId))
                .build();
    }
} 