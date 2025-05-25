package com.phuc.call_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_messages")
public class ChatMessage {
    @Id
    private String id;
    private String groupId;
    private String senderId;
    private String senderName;
    private String content;
    private Instant createdAt;
} 