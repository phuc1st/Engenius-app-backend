package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.request.GroupStudyCreateRequest;
import com.phuc.learn_service.dto.GroupStudyResponse;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.service.GroupStudyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/group-study")
public class GroupStudyController {
    GroupStudyService groupStudyService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<GroupStudyResponse> createGroup(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar) {

        // Gọi service để tạo nhóm học
        GroupStudyResponse response = groupStudyService.createGroup(name, description, avatar);

        return ApiResponse.<GroupStudyResponse>builder()
                .result(response)
                .build();
    }

} 