package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.request.UserFlashCardUpdateRequest;
import com.phuc.learn_service.dto.response.UserFlashCardProgressResponse;
import com.phuc.learn_service.dto.response.UserVocabularyTopicProgressResponse;
import com.phuc.learn_service.entity.FlashCard;
import com.phuc.learn_service.entity.UserFlashCardProgress;
import com.phuc.learn_service.entity.UserVocabularyTopicProgress;
import com.phuc.learn_service.entity.VocabularyTopic;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.UserFlashCardProgressMapper;
import com.phuc.learn_service.mapper.UserVocabularyTopicProgressMapper;
import com.phuc.learn_service.repository.FlashCardRepository;
import com.phuc.learn_service.repository.UserFlashCardProgressRepository;
import com.phuc.learn_service.repository.UserVocabularyTopicProgressRepository;
import com.phuc.learn_service.repository.VocabularyTopicRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    UserFlashCardProgressMapper userFlashCardProgressMapper;

    public UserVocabularyTopicProgressResponse createTopicProgress(Long topicId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
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

        return userVocabularyTopicProgressMapper
                .toUserVocabularyTopicProgressResponse(
                        vocabularyTopicProgressRepository.save(progress));
    }

    public void updateFlashCardStatus(UserFlashCardUpdateRequest request) {
        FlashCard flashCard = flashCardRepository.findById(request.getFlashCardId())
                .orElseThrow(() -> new AppException(ErrorCode.FLASH_CARD_NOT_EXIST));

        UserVocabularyTopicProgress topicProgress = vocabularyTopicProgressRepository
                .findById(request.getProgressId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_VOCAB_TOPIC_PROGRESS_NOT_FOUND));

        Optional<UserFlashCardProgress> existing = flashCardProgressRepository
                .findByProgressIdAndFlashCardId(request.getProgressId(), request.getFlashCardId());

        if (existing.isPresent()) {
            UserFlashCardProgress p = existing.get();
            boolean wasMemorized = p.isMemorized();
            p.setMemorized(request.isMemorized());
            p.setLastReviewed(LocalDateTime.now());
            flashCardProgressRepository.save(p);

            // cập nhật số lượng nếu trạng thái thay đổi
            if (wasMemorized != request.isMemorized()) {
                if (request.isMemorized()) {
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
                    .memorized(request.isMemorized())
                    .lastReviewed(LocalDateTime.now())
                    .build();

            flashCardProgressRepository.save(p);

            topicProgress.setStudied(topicProgress.getStudied() + 1);
            if (request.isMemorized()) {
                topicProgress.setMemorized(topicProgress.getMemorized() + 1);
            } else {
                topicProgress.setUnmemorized(topicProgress.getUnmemorized() + 1);
            }
        }

        vocabularyTopicProgressRepository.save(topicProgress);
    }

    public List<UserVocabularyTopicProgressResponse> getAllProgressWithDefaults() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
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

    public List<UserFlashCardProgressResponse> getTopicProgress(Long progressId) {
        UserVocabularyTopicProgress topicProgress = vocabularyTopicProgressRepository.findById(progressId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_VOCAB_TOPIC_PROGRESS_NOT_FOUND));

        // Lấy toàn bộ flashcard của topic
        List<FlashCard> allFlashCards = flashCardRepository
                .findByTopicId(topicProgress.getVocabularyTopic().getId());

        // Lấy tất cả progress hiện có của user cho topic này
        List<UserFlashCardProgress> existingProgresses = flashCardProgressRepository.findByProgressId(progressId);
        Map<Long, UserFlashCardProgress> progressMap = new HashMap<>();
        for (UserFlashCardProgress existingProgress : existingProgresses) {
            progressMap.put(existingProgress.getFlashCard().getId(), existingProgress);
        }

        // Tạo danh sách các FlashCardProgress chưa có ⇒ thêm mới
        List<UserFlashCardProgress> toInsert = new ArrayList<>();
        for (FlashCard flashCard : allFlashCards) {
            if (!progressMap.containsKey(flashCard.getId())) {
                UserFlashCardProgress newProgress = UserFlashCardProgress.builder()
                        .flashCard(flashCard)
                        .progress(topicProgress)
                        .memorized(false)
                        .lastReviewed(null)
                        .build();
                toInsert.add(newProgress);
            }
        }

        // Batch insert 1 lần duy nhất
        if (!toInsert.isEmpty()) {
            flashCardProgressRepository.saveAll(toInsert);
            existingProgresses.addAll(toInsert); // để trả về cả phần mới thêm
        }

        // Mapping sang response DTO
        return existingProgresses.stream()
                .map(userFlashCardProgressMapper::toUserFlashCardProgressResponse).toList();
    }
}
//thay đôi response, request, them getflashcardprogress 