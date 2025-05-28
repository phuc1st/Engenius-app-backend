package com.phuc.profile.controller;

import com.phuc.profile.dto.ApiResponse;
import com.phuc.profile.dto.request.ProfileGroupCreateRequest;
import com.phuc.profile.dto.response.GroupNodeResponse;
import com.phuc.profile.dto.response.UserProfileResponse;
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
        groupNodeService.createGroupNode(request);
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

    @GetMapping("users-in-group")
    public ApiResponse<List<UserProfileResponse>> getUsersInGroup(@RequestParam String groupId){
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(groupNodeService.getUsersInGroup(groupId))
                .build();
    }
} 