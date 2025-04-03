package com.phuc.identity.mapper;

import org.mapstruct.Mapper;

import com.phuc.identity.dto.request.ProfileCreationRequest;
import com.phuc.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
