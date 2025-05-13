package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.request.GroupStudyCreateRequest;
import com.phuc.learn_service.dto.GroupStudyResponse;
import com.phuc.learn_service.entity.GroupStudy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupStudyMapper {
    GroupStudy toEntity(GroupStudyCreateRequest req);
    GroupStudyResponse toResponse(GroupStudy entity);
} 