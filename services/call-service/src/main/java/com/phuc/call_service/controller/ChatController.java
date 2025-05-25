package com.phuc.call_service.controller;

import com.phuc.call_service.dto.response.ApiResponse;
import com.phuc.call_service.entity.ChatMessage;
import com.phuc.call_service.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageRepository chatMessageRepository;

    @PostMapping("/group/{groupId}/message")
    public ChatMessage sendMessage(@PathVariable String groupId, @RequestBody SendMessageRequest request) {
        ChatMessage message = ChatMessage.builder()
                .groupId(groupId)
                .senderId(request.getSenderId())
                .senderName(request.getSenderName())
                .content(request.getContent())
                .createdAt(Instant.now())
                .build();
        return chatMessageRepository.save(message);
    }

    @GetMapping("/group/{groupId}/messages")
    public ApiResponse<List<ChatMessage>> getMessages(@PathVariable String groupId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Lấy tin nhắn mới nhất trước, sau đó đảo ngược lại để hiển thị đúng thứ tự cũ -> mới
        List<ChatMessage> messages = chatMessageRepository.findByGroupIdOrderByCreatedAtDesc(groupId, pageable);
        Collections.reverse(messages);
        return ApiResponse.<List<ChatMessage>>builder()
                .result(messages)
                .build();
    }

    @lombok.Data
    public static class SendMessageRequest {
        private String senderId;
        private String senderName;
        private String content;
    }
} 