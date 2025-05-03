package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.request.FlashCardCreationRequest;
import com.phuc.learn_service.dto.request.UserFlashCardUpdateRequest;
import com.phuc.learn_service.dto.request.UserVocabularyTopicProgressCreationRequest;
import com.phuc.learn_service.dto.request.VocabularyTopicCreationRequest;
import com.phuc.learn_service.dto.response.*;
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

    @PutMapping("update-user-flashcard")
    public ApiResponse<Void> updateFlashCardStatus(@RequestBody UserFlashCardUpdateRequest request) {
        vocabularyProgressService.updateFlashCardStatus(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/start")
    public ApiResponse<UserVocabularyTopicProgressResponse> startLearning(
            @RequestBody UserVocabularyTopicProgressCreationRequest request) {
        return ApiResponse.<UserVocabularyTopicProgressResponse>builder()
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

    @GetMapping("{progressId}")
    public ApiResponse<List<UserFlashCardProgressResponse>> getTopicProgress(
            @PathVariable Long progressId
    ) {
        return ApiResponse.<List<UserFlashCardProgressResponse>>builder()
                .result(vocabularyProgressService.getTopicProgress(progressId))
                .build();
    }

}
