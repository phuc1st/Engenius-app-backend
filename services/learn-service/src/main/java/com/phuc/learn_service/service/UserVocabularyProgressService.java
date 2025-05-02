package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.response.UserVocabularyTopicProgressResponse;
import com.phuc.learn_service.entity.FlashCard;
import com.phuc.learn_service.entity.UserFlashCardProgress;
import com.phuc.learn_service.entity.UserVocabularyTopicProgress;
import com.phuc.learn_service.entity.VocabularyTopic;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.UserVocabularyTopicProgressMapper;
import com.phuc.learn_service.repository.FlashCardRepository;
import com.phuc.learn_service.repository.UserFlashCardProgressRepository;
import com.phuc.learn_service.repository.UserVocabularyTopicProgressRepository;
import com.phuc.learn_service.repository.VocabularyTopicRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserVocabularyProgressService {
    UserVocabularyTopicProgressRepository vocabularyTopicProgressRepository;
    UserFlashCardProgressRepository flashCardProgressRepository;
    VocabularyTopicRepository vocabularyTopicRepository;
    FlashCardRepository flashCardRepository;

    UserVocabularyTopicProgressMapper userVocabularyTopicProgressMapper;

    public UserVocabularyTopicProgress createTopicProgress(String userId, Long topicId) {
        VocabularyTopic topic = vocabularyTopicRepository.findById(topicId)
                .orElseThrow(() -> new AppException(ErrorCode.VOCABULARY_TOPIC_NOT_EXIST));

        boolean exists = vocabularyTopicProgressRepository.existsByUserIdAndVocabularyTopicId(userId, topicId);
        if (exists) {
            throw new AppException(ErrorCode.USER_VOCAB_TOPIC_PROGRESS_EXIST);
        }

        UserVocabularyTopicProgress progress = UserVocabularyTopicProgress.builder()
                .vocabularyTopic(topic)
                .userId(userId)
                .studied(0)
                .unmemorized(0)
                .memorized(0)
                .build();

        return vocabularyTopicProgressRepository.save(progress);
    }

    public void updateFlashCardStatus(Long progressId, Long flashCardId, boolean memorized) {
        FlashCard flashCard = flashCardRepository.findById(flashCardId)
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_CARD_NOT_EXIST));
        UserVocabularyTopicProgress topicProgress = vocabularyTopicProgressRepository.findById(progressId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_VOCAB_TOPIC_PROGRESS_NOT_FOUND));

        Optional<UserFlashCardProgress> existing = flashCardProgressRepository
                .findByProgressIdAndFlashCardId(progressId, flashCardId);

        if (existing.isPresent()) {
            UserFlashCardProgress p = existing.get();
            boolean wasMemorized = p.isMemorized();
            p.setMemorized(memorized);
            p.setLastReviewed(LocalDateTime.now());
            flashCardProgressRepository.save(p);

            // cập nhật số lượng nếu trạng thái thay đổi
            if (wasMemorized != memorized) {
                if (memorized) {
                    topicProgress.setMemorized(topicProgress.getMemorized() + 1);
                    topicProgress.setUnmemorized(topicProgress.getUnmemorized() - 1);
                } else {
                    topicProgress.setMemorized(topicProgress.getMemorized() - 1);
                    topicProgress.setUnmemorized(topicProgress.getUnmemorized() + 1);
                }
            }

        } else {
            // flashcard lần đầu được học
            UserFlashCardProgress p = UserFlashCardProgress.builder()
                    .flashCard(flashCard)
                    .progress(topicProgress)
                    .memorized(memorized)
                    .lastReviewed(LocalDateTime.now())
                    .build();

            flashCardProgressRepository.save(p);

            topicProgress.setStudied(topicProgress.getStudied() + 1);
            if (memorized) {
                topicProgress.setMemorized(topicProgress.getMemorized() + 1);
            } else {
                topicProgress.setUnmemorized(topicProgress.getUnmemorized() + 1);
            }
        }

        vocabularyTopicProgressRepository.save(topicProgress);
    }

    public List<UserVocabularyTopicProgressResponse> getAllProgressWithDefaults(String userId) {
        List<VocabularyTopic> allTopics = vocabularyTopicRepository.findAll();
        Map<Long, UserVocabularyTopicProgress> userProgressMap = vocabularyTopicProgressRepository
                .findByUserId(userId)
                .stream()
                .collect(Collectors.toMap(p -> p.getVocabularyTopic().getId(), p -> p));

        List<UserVocabularyTopicProgress> result = new ArrayList<>();

        for (VocabularyTopic topic : allTopics) {
            if (userProgressMap.containsKey(topic.getId())) {
                result.add(userProgressMap.get(topic.getId()));
            } else {
                // Trả về UserTopicProgress mặc định
                UserVocabularyTopicProgress emptyProgress = UserVocabularyTopicProgress.builder()
                        .vocabularyTopic(topic)
                        .userId(userId)
                        .studied(0)
                        .memorized(0)
                        .unmemorized(0)
                        .build();
                result.add(emptyProgress);
            }
        }

        return result.stream().map(
                userVocabularyTopicProgressMapper::toUserVocabularyTopicProgressResponse).toList();
    }

}
