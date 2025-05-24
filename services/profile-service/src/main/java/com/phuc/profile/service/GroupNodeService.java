package com.phuc.profile.service;

import com.phuc.profile.dto.response.GroupNodeResponse;
import com.phuc.profile.dto.response.GroupStatsDTO;
import com.phuc.profile.entity.GroupNode;
import com.phuc.profile.repository.GroupNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        group = groupNodeRepository.save(group);
        
        // Tạo quan hệ CREATED_BY giữa người dùng và nhóm
        groupNodeRepository.createCreatedByRelationship(createdBy, id);
        
        return group;
    }

    public List<GroupNodeResponse> getGroups(int page, int size, String searchQuery) {
        long skip = (long) page * size;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<GroupStatsDTO> result = groupNodeRepository.findGroupsWithStats(
                userId,
                (searchQuery != null && !searchQuery.isBlank()) ? searchQuery : null,
                skip,
                size
        );

        return result.stream().map(map -> {
            GroupNode group = map.getG();
            Long memberCount = map.getMemberCount();
            Boolean joined = map.getJoined();

            return GroupNodeResponse.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .createdBy(group.getCreatedBy())
                    .memberCount(memberCount != null ? memberCount.intValue() : 0)
                    .joined(Boolean.TRUE.equals(joined))
                    .build();
        }).toList();
    }

    public void joinGroup(String groupId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        groupNodeRepository.joinGroup(userId, groupId);
    }

} 