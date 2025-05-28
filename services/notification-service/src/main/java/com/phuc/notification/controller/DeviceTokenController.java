package com.phuc.notification.controller;

import com.phuc.notification.dto.ApiResponse;
import com.phuc.notification.service.DeviceTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceTokenController {
    DeviceTokenService deviceTokenService;

    @PostMapping("device-token")
    ApiResponse<Void> createDeviceToken(@RequestParam String deviceToken){
        deviceTokenService.createDeviceToken(deviceToken);
        return ApiResponse.<Void>builder().build();
    }
}
