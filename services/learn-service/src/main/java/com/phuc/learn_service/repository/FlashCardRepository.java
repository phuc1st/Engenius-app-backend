package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByTopicId(Long vocabTopicId);
}