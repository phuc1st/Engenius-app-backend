package com.phuc.profile.mapper;

import com.phuc.profile.dto.request.ProfileGroupCreateRequest;
import com.phuc.profile.entity.GroupNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupNodeMapper {
    @Mapping(source = "groupId", target = "id")
    @Mapping(source = "groupName", target = "name")
    GroupNode toGroupNode(ProfileGroupCreateRequest request);
}
