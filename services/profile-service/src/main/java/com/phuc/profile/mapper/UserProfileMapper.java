package com.phuc.profile.mapper;

import org.mapstruct.Mapper;

import com.phuc.profile.dto.request.ProfileCreationRequest;
import com.phuc.profile.dto.response.UserProfileResponse;
import com.phuc.profile.entity.UserProfile;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    @Mapping(source = "userId", target = "id")
    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
