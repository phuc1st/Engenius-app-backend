package com.phuc.call_service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phuc.call_service.entity.ChatMessage;
import com.phuc.call_service.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    // Map userId -> session
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    // Map groupId -> Set<userId>
    private final Map<String, Set<String>> groupMembers = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Lấy userId và groupId từ query param (giả sử client truyền vào)
        String userId = getParam(session, "userId");
        String groupId = getParam(session, "groupId");
        if (userId != null) {
            sessions.put(userId, session);
            if (groupId != null) {
                groupMembers.computeIfAbsent(groupId, k -> new HashSet<>()).add(userId);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Xóa user khỏi sessions và groupMembers
        String userId = getParam(session, "userId");
        String groupId = getParam(session, "groupId");
        if (userId != null) {
            sessions.remove(userId);
            if (groupId != null) {
                Set<String> members = groupMembers.get(groupId);
                if (members != null) {
                    members.remove(userId);
                    if (members.isEmpty()) {
                        groupMembers.remove(groupId);
                    }
                }
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
        String type = (String) payload.get("type");
        if ("chat".equals(type)) {
            handleChatMessage(payload);
        }
    }

    private void handleChatMessage(Map<String, Object> payload) {
        try {
            String groupId = (String) payload.get("groupId");
            String senderId = (String) payload.get("from");
            String senderName = (String) payload.get("senderName");
            String content = (String) payload.get("content");

            // 1. Lưu vào MongoDB
            ChatMessage chatMessage = ChatMessage.builder()
                    .groupId(groupId)
                    .senderId(senderId)
                    .senderName(senderName)
                    .content(content)
                    .createdAt(Instant.now())
                    .build();
            chatMessageRepository.save(chatMessage);

            // 2. Broadcast cho các thành viên trong group
            Set<String> members = groupMembers.getOrDefault(groupId, Collections.emptySet());
            for (String memberId : members) {
                WebSocketSession ws = sessions.get(memberId);
                if (ws != null && ws.isOpen()) {
                    ws.sendMessage(new TextMessage(objectMapper.writeValueAsString(
                            Map.of(
                                    "type", "chat",
                                    "groupId", groupId,
                                    "from", senderId,
                                    "senderName", senderName,
                                    "content", content,
                                    "createdAt", chatMessage.getCreatedAt().toString()
                            )
                    )));
                }
            }
        } catch (Exception e) {
            System.err.println("Error handleChatMessage: " + e.getMessage());
        }
    }

    private String getParam(WebSocketSession session, String key) {
        List<String> values = session.getUri().getQuery() != null ? Arrays.asList(session.getUri().getQuery().split("&")) : Collections.emptyList();
        for (String param : values) {
            String[] pair = param.split("=");
            if (pair.length == 2 && pair[0].equals(key)) {
                return pair[1];
            }
        }
        return null;
    }
}