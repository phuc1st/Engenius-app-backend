package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.VocabularyTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VocabularyTopicRepository extends JpaRepository<VocabularyTopic, Long> {
    boolean existsByTopicName(String vocabularyTopicName);
}
