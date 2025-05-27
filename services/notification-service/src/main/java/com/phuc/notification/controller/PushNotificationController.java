package com.phuc.notification.controller;

import com.phuc.notification.dto.ApiResponse;
import com.phuc.notification.service.PushNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PushNotificationController {
    PushNotificationService pushNotificationService;

    @PostMapping("/push")
    ApiResponse<Void> pushNotification(@RequestParam String deviceId) {
        pushNotificationService.sendPushNotification(deviceId, "test", "test");
        return ApiResponse.<Void>builder().build();
    }
}
