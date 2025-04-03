package com.phuc.identity.mapper;

import org.mapstruct.Mapper;

import com.phuc.identity.dto.request.PermissionRequest;
import com.phuc.identity.dto.response.PermissionResponse;
import com.phuc.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
