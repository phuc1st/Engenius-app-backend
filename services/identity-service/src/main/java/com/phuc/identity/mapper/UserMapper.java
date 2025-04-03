package com.phuc.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.phuc.identity.dto.request.UserCreationRequest;
import com.phuc.identity.dto.request.UserUpdateRequest;
import com.phuc.identity.dto.response.UserResponse;
import com.phuc.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
