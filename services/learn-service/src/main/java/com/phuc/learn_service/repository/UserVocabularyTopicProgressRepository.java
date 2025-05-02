package com.phuc.learn_service.repository;

import com.phuc.learn_service.entity.UserVocabularyTopicProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVocabularyTopicProgressRepository extends
        JpaRepository<UserVocabularyTopicProgress, Long> {
    boolean existsByUserIdAndVocabularyTopicId(String userId, Long topicId);
    List<UserVocabularyTopicProgress> findByUserId(String userId);
}