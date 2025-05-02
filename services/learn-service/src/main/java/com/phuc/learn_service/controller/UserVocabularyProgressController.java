package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.request.FlashCardCreationRequest;
import com.phuc.learn_service.dto.request.UserVocabularyTopicProgressCreationRequest;
import com.phuc.learn_service.dto.request.VocabularyTopicCreationRequest;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.dto.response.FlashCardResponse;
import com.phuc.learn_service.dto.response.UserVocabularyTopicProgressResponse;
import com.phuc.learn_service.dto.response.VocabularyTopicResponse;
import com.phuc.learn_service.entity.UserVocabularyTopicProgress;
import com.phuc.learn_service.service.UserVocabularyProgressService;
import com.phuc.learn_service.service.VocabularyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress/vocabulary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserVocabularyProgressController {
    UserVocabularyProgressService vocabularyProgressService;

    @PutMapping("/progress/{progressId}/flashcard/{flashCardId}")
    public ApiResponse<Void> updateFlashCardStatus(
            @PathVariable Long progressId,
            @PathVariable Long flashCardId,
            @RequestParam boolean memorized
    ) {
        vocabularyProgressService.updateFlashCardStatus(progressId, flashCardId, memorized);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/start")
    public ApiResponse<UserVocabularyTopicProgress> startLearning(
            @RequestBody UserVocabularyTopicProgressCreationRequest request) {
        return ApiResponse.<UserVocabularyTopicProgress>builder()
                .result(vocabularyProgressService.createTopicProgress(
                        request.getUserId(), request.getTopicId()))
                .build();
    }

    @GetMapping("/user/{userId}/full-progress")
    public ApiResponse<List<UserVocabularyTopicProgressResponse>> getFullProgress(@PathVariable String userId) {
        return ApiResponse.<List<UserVocabularyTopicProgressResponse>>builder()
                .result(vocabularyProgressService.getAllProgressWithDefaults(userId))
                .build();
    }

}
