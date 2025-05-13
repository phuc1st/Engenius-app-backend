package com.phuc.profile.service;

import com.phuc.profile.entity.GroupNode;
import com.phuc.profile.repository.GroupNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupNodeService {
    @Autowired
    GroupNodeRepository groupNodeRepository;

    public GroupNode createGroupNode(String id, String name, String createdBy) {
        GroupNode group = GroupNode.builder()
                .id(id)
                .name(name)
                .createdBy(createdBy)
                .build();
        return groupNodeRepository.save(group);
    }
} 