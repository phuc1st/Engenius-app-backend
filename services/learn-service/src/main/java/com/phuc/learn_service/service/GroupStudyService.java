package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.request.GroupStudyCreateRequest;
import com.phuc.learn_service.dto.GroupStudyResponse;
import com.phuc.learn_service.dto.request.ProfileGroupCreateRequest;
import com.phuc.learn_service.entity.GroupStudy;
import com.phuc.learn_service.mapper.GroupStudyMapper;
import com.phuc.learn_service.repository.GroupStudyRepository;
import com.phuc.learn_service.repository.httpclient.ProfileServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupStudyService {
    private final GroupStudyRepository groupStudyRepository;
    private final GroupStudyMapper groupStudyMapper;
    private final ProfileServiceClient profileServiceClient;

    @Transactional
    public GroupStudyResponse createGroup(GroupStudyCreateRequest request) {
        GroupStudy group = groupStudyMapper.toEntity(request);
        group = groupStudyRepository.save(group);
        // Gọi sang profile-service để tạo node group và quan hệ
        profileServiceClient.createGroupNode(
                ProfileGroupCreateRequest.builder()
                        .groupId(group.getId().toString())
                        .groupName(group.getName())
                        .createdBy(group.getCreatedBy())
                        .build()
        );
        return groupStudyMapper.toResponse(group);
    }
} 