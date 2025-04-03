package com.phuc.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.phuc.identity.dto.request.RoleRequest;
import com.phuc.identity.dto.response.RoleResponse;
import com.phuc.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
