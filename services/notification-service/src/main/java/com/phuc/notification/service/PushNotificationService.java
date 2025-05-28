package com.phuc.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.phuc.notification.dto.response.UserProfileResponse;
import com.phuc.notification.entity.DeviceToken;
import com.phuc.notification.repository.DeviceTokenRepository;
import com.phuc.notification.repository.httpclient.ProfileServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PushNotificationService {
    ProfileServiceClient profileServiceClient;
    DeviceTokenRepository deviceTokenRepository;

    public void sendPushNotification(String groupId, String senderId, String body) {
        //getgroup user tu profileLisist<
        List<UserProfileResponse> result = profileServiceClient.getUsersInGroup(
                groupId).getResult();
        result.forEach(userProfileResponse -> {
            if (!userProfileResponse.getId().equals(senderId)) {
                Optional<DeviceToken> deviceToken = deviceTokenRepository.findByUserId(
                        userProfileResponse.getId());
                try {
                    if (deviceToken.isPresent()) {
                        Message message = Message.builder()
                                .setNotification(Notification.builder()
                                        .setTitle(userProfileResponse.getUsername() + ": sent")
                                        .setBody(body)
                                        .build())
                                .setToken(deviceToken.get().getDeviceToken())
                                .build();

                        String response = FirebaseMessaging.getInstance().send(message);
                        log.info("Successfully sent message: {}", response);
                    }
                } catch (Exception e) {
                    log.error("Error sending push notification", e);
                }
            }
        });
        /*try {
            if (deviceToken != null) {
                Message message = Message.builder()
                    .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                    .setToken(deviceToken)
                    .build();
                    
                String response = FirebaseMessaging.getInstance().send(message);
                log.info("Successfully sent message: {}", response);
            }
        } catch (Exception e) {
            log.error("Error sending push notification", e);
        }*/
    }
}