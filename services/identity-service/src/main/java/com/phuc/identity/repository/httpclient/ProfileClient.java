package com.phuc.identity.repository.httpclient;

import com.phuc.identity.configuration.AuthenticationRequestInterceptor;
import com.phuc.identity.dto.request.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.phuc.identity.dto.request.ProfileCreationRequest;
import com.phuc.identity.dto.response.UserProfileResponse;

@FeignClient(name = "profile-service",
        configuration = { AuthenticationRequestInterceptor.class })
public interface ProfileClient {
    @PostMapping(value = "/profile/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
