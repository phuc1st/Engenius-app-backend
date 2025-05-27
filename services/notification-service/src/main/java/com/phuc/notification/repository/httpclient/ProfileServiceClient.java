package com.phuc.notification.repository.httpclient;

import com.phuc.notification.dto.ApiResponse;
import com.phuc.notification.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "PROFILE-SERVICE", url = "${app.services.profile}")
public interface ProfileServiceClient {
    @GetMapping(value = "/internal/group-node/users-in-group", produces =
            MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<UserProfileResponse>> getUsersInGroup(@RequestParam String groupId);
} 