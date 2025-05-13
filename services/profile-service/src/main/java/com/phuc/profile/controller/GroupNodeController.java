package com.phuc.profile.controller;

import com.phuc.profile.service.GroupNodeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Data
    public static class ProfileGroupCreateRequest {
        private String groupId;
        private String groupName;
        private String createdBy;
    }
} 