package com.phuc.notification.controller;

import com.phuc.event.dto.NotificationEvent;
import com.phuc.notification.dto.request.Recipient;
import com.phuc.notification.dto.request.SendEmailRequest;
import com.phuc.notification.service.EmailService;
import com.phuc.notification.service.PushNotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    EmailService emailService;
    PushNotificationService pushNotificationService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        if (message.getChannel().equals("CHAT")) {
            pushNotificationService.sendPushNotification(
                    message.getRecipient(),
                    message.getSubject(),
                    message.getBody());
        } else {
            log.info("Message received: {}", message);
            emailService.sendEmail(SendEmailRequest.builder().to(
                    Recipient.builder().email(message.getRecipient()).build())
                    .subject(message.getSubject())
                    .htmlContent(message.getBody()).build());
        }
    }
}
