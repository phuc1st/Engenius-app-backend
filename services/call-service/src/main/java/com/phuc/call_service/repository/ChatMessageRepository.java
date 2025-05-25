package com.phuc.call_service.repository;

import com.phuc.call_service.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByGroupIdOrderByCreatedAtAsc(String groupId);
    List<ChatMessage> findByGroupIdOrderByCreatedAtDesc(String groupId, Pageable pageable);
} 