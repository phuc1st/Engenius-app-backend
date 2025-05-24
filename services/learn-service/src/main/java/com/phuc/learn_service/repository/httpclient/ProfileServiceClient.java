package com.phuc.learn_service.repository.httpclient;

import com.phuc.learn_service.configuration.AuthenticationRequestInterceptor;
import com.phuc.learn_service.dto.request.ProfileGroupCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PROFILE-SERVICE", configuration = { AuthenticationRequestInterceptor.class })
public interface ProfileServiceClient {
    @PostMapping(value = "/profile/internal/group-node", produces = MediaType.APPLICATION_JSON_VALUE)
    void createGroupNode(@RequestBody ProfileGroupCreateRequest request);
} 