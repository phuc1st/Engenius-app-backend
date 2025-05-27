package com.phuc.call_service.service;

import com.phuc.event.dto.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    
    public void sendChatNotification(String groupId, String senderId, String content) {
        NotificationEvent event = NotificationEvent.builder()
            .channel("CHAT")
            .recipient(groupId)
            .subject(senderId)
            .body(content)
            .build();
            
        kafkaTemplate.send("notification-delivery", event);
    }
} 