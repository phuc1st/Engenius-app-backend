package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.request.GroupStudyCreateRequest;
import com.phuc.learn_service.dto.GroupStudyResponse;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.service.GroupStudyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/group-study")
public class GroupStudyController {
    GroupStudyService groupStudyService;

    @PostMapping
    public ApiResponse<GroupStudyResponse> createGroup(@RequestBody GroupStudyCreateRequest request) {
        return ApiResponse.<GroupStudyResponse>builder()
                .result(groupStudyService.createGroup(request))
                .build();
    }
} 