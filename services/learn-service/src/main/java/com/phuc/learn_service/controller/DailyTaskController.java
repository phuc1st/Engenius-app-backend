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

    @GetMapping()
    public ApiResponse<List<DailyTask>> getDailyTasks() {
        return ApiResponse.<List<DailyTask>>builder()
                .result(dailyTaskService.getDailyTasks())
                .build();
    }

    @PostMapping("/{taskId}/complete")
    public ApiResponse<DailyTask> completeTask(
        @PathVariable Long taskId
    ) {
        return ApiResponse.<DailyTask>builder()
                .result(dailyTaskService.completeTask(taskId))
                .build();
    }

    @GetMapping("/progress")
    public ApiResponse<DailyTaskService.DailyTaskProgress> getTaskProgress() {
        return ApiResponse.<DailyTaskService.DailyTaskProgress>builder()
                .result(dailyTaskService.getTaskProgress())
                .build();
    }
} 