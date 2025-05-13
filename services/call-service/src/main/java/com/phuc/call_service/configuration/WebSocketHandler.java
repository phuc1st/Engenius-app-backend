    package com.phuc.call_service.configuration;

    import org.springframework.stereotype.Component;
    import org.springframework.web.socket.*;
    import org.springframework.web.socket.handler.TextWebSocketHandler;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import java.util.*;
    import java.util.concurrent.ConcurrentHashMap;

    @Component
    public class WebSocketHandler extends TextWebSocketHandler {
        private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
        private final Map<String, String> userToPartnerMap = new ConcurrentHashMap<>(); // Theo dõi cặp đôi
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            String userId = extractUserId(session);
            sessions.put(userId, session);
            System.out.println("🔌 User connected: " + userId);

            // Thông báo cho user về trạng thái kết nối
            sendMessage(userId, Map.of("type", "connectionStatus", "status", "connected"));

            // Tìm partner nếu không có trong cặp đôi
            if (!userToPartnerMap.containsKey(userId)) {
                findPartner(userId);
            }
        }

        private String extractUserId(WebSocketSession session) {
            try {
                return Objects.requireNonNull(session.getUri()).getQuery().split("=")[1];
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid user ID in WebSocket URL");
            }
        }

        private void findPartner(String userId) {
            System.out.println("🔍 Looking for partner for user: " + userId);
            System.out.println("📌 Active users: " + sessions.keySet());
            System.out.println("📌 Current pairings: " + userToPartnerMap);

            Optional<String> partnerId = sessions.keySet().stream()
                    .filter(id -> !id.equals(userId))
                    .filter(id -> !userToPartnerMap.containsKey(id)) // Chỉ ghép với người chưa có partner
                    .findFirst();

            if (partnerId.isPresent()) {
                String pid = partnerId.get();
                userToPartnerMap.put(userId, pid);
                userToPartnerMap.put(pid, userId);

                System.out.println("✅ Partner found! " + userId + " <--> " + pid);
                notifyPartnerFound(userId, pid);
                notifyPartnerFound(pid, userId);
            } else {
                System.out.println("❌ No partner available for user: " + userId);
            }
        }


        private void notifyPartnerFound(String userId, String partnerId) {
            sendMessage(userId, Map.of(
                    "type", "partnerFound",
                    "partnerId", partnerId
            ));
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            try {
                Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
                String type = (String) payload.get("type");
                String from = (String) payload.get("from");

                switch (type) {
                    case "findPartner":
                        handleFindPartner(from);
                        break;
                    case "disconnect":
                        handleDisconnect(from);
                        break;
                    default:
                        String to = (String) payload.get("to");
                        if (to != null && sessions.containsKey(to)) {
                            sessions.get(to).sendMessage(message);
                        }
                }
            } catch (Exception e) {
                System.err.println("Error processing message: " + e.getMessage());
            }
        }

        private void handleFindPartner(String userId) {
            if (userToPartnerMap.containsKey(userId)) {
                // Nếu đã có partner, thông báo lại partnerId
                sendMessage(userId, Map.of(
                        "type", "partnerFound",
                        "partnerId", userToPartnerMap.get(userId)
                ));
            } else {
                findPartner(userId);
            }
        }

        private void handleDisconnect(String userId) {
            String partnerId = userToPartnerMap.get(userId);
            if (partnerId != null) {
                // Thông báo cho partner về việc ngắt kết nối
                sendMessage(partnerId, Map.of("type", "partnerDisconnected"));
                userToPartnerMap.remove(partnerId);
            }
            userToPartnerMap.remove(userId);
            sessions.remove(userId);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            String userId = sessions.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(session))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);

            if (userId != null) {
                handleDisconnect(userId);
                System.out.println("🔴 User disconnected: " + userId + ", reason: " + status.getReason());
            }
        }

        private void sendMessage(String userId, Map<String, Object> message) {
            try {
                WebSocketSession session = sessions.get(userId);
                if (session != null && session.isOpen()) {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                }
            } catch (Exception e) {
                System.err.println("Failed to send message to user " + userId + ": " + e.getMessage());
            }
        }
    }