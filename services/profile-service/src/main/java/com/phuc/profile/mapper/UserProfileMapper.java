package com.phuc.profile.mapper;

import org.mapstruct.Mapper;

import com.phuc.profile.dto.request.ProfileCreationRequest;
import com.phuc.profile.dto.response.UserProfileResponse;
import com.phuc.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileReponse(UserProfile entity);
}
