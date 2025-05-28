package com.phuc.notification.service;

import com.phuc.notification.dto.request.EmailRequest;
import com.phuc.notification.dto.request.SendEmailRequest;
import com.phuc.notification.dto.request.Sender;
import com.phuc.notification.dto.response.EmailResponse;
import com.phuc.notification.entity.DeviceToken;
import com.phuc.notification.exception.AppException;
import com.phuc.notification.exception.ErrorCode;
import com.phuc.notification.repository.DeviceTokenRepository;
import com.phuc.notification.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeviceTokenService {
    DeviceTokenRepository deviceTokenRepository;

    public void createDeviceToken(String deviceToken){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<DeviceToken> deviceTokenExist = deviceTokenRepository.findByUserId(userId);

        if(deviceTokenExist.isPresent()){
            deviceTokenExist.get().setDeviceToken(deviceToken);
            deviceTokenRepository.save(deviceTokenExist.get());
        }else {
            DeviceToken deviceTokenEntity = DeviceToken.builder()
                    .userId(userId)
                    .deviceToken(deviceToken)
                    .build();
            deviceTokenRepository.save(deviceTokenEntity);
        }

    }
}
