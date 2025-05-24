package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.GroupStudyResponse;
import com.phuc.learn_service.dto.request.ProfileGroupCreateRequest;
import com.phuc.learn_service.entity.GroupStudy;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.GroupStudyMapper;
import com.phuc.learn_service.repository.GroupStudyRepository;
import com.phuc.learn_service.repository.httpclient.FileClient;
import com.phuc.learn_service.repository.httpclient.ProfileServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupStudyService {
    final GroupStudyRepository groupStudyRepository;
    final GroupStudyMapper groupStudyMapper;
    final ProfileServiceClient profileServiceClient;
    final FileClient fileClient;

    @Transactional
    public GroupStudyResponse createGroup(String name, String description, MultipartFile avatar) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        String avatarUrl = "";
        if (avatar != null)
            avatarUrl = fileClient.uploadMedia(avatar).getResult().getUrl();

        GroupStudy groupStudy = GroupStudy.builder()
                .avatar(avatarUrl)
                .name(name)
                .description(description)
                .createdBy(userId)
                .build();

        try {
            groupStudy = groupStudyRepository.save(groupStudy);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.GROUP_EXISTED);
        }
        // Gọi sang profile-service để tạo node group và quan hệ
        profileServiceClient.createGroupNode(
                ProfileGroupCreateRequest.builder()
                        .groupId(groupStudy.getId().toString())
                        .groupName(groupStudy.getName())
                        .createdBy(groupStudy.getCreatedBy())
                        .build()
        );
        return groupStudyMapper.toResponse(groupStudy);
    }
} 