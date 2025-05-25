package com.phuc.profile.controller;

import com.phuc.profile.dto.ApiResponse;
import com.phuc.profile.dto.response.GroupNodeResponse;
import com.phuc.profile.service.GroupNodeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/internal/group-node")
public class GroupNodeController {
    GroupNodeService groupNodeService;

    @PostMapping
    public void createGroupNode(@RequestBody ProfileGroupCreateRequest request) {
        groupNodeService.createGroupNode(request.getGroupId(), request.getGroupName(), request.getCreatedBy());
    }

    @GetMapping
    public ApiResponse<List<GroupNodeResponse>> getGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchQuery) {

        List<GroupNodeResponse> groups = groupNodeService.getGroups(page, size, searchQuery);
        
        return ApiResponse.<List<GroupNodeResponse>>builder()
                .result(groups)
                .build();
    }

    @PostMapping("/{groupId}/join")
    public ApiResponse<Void> joinGroup(@PathVariable String groupId) {
        groupNodeService.joinGroup(groupId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/joined")
    public ApiResponse<List<GroupNodeResponse>> getJoinedGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<GroupNodeResponse> groups = groupNodeService.getJoinedGroups(page, size);
        
        return ApiResponse.<List<GroupNodeResponse>>builder()
                .result(groups)
                .build();
    }

    @Data
    public static class ProfileGroupCreateRequest {
        private String groupId;
        private String groupName;
        private String createdBy;
    }
} 