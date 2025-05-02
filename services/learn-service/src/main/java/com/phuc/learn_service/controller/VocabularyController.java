package com.phuc.learn_service.controller;

import com.phuc.learn_service.dto.request.FlashCardCreationRequest;
import com.phuc.learn_service.dto.request.VocabularyTopicCreationRequest;
import com.phuc.learn_service.dto.response.ApiResponse;
import com.phuc.learn_service.dto.response.FlashCardResponse;
import com.phuc.learn_service.dto.response.VocabularyTopicResponse;
import com.phuc.learn_service.service.VocabularyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vocabulary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VocabularyController {
    VocabularyService vocabularyService;

    @PostMapping("createTopic")
    public ApiResponse<VocabularyTopicResponse> createTopic(
            @RequestBody @Valid VocabularyTopicCreationRequest request) {
        return ApiResponse.<VocabularyTopicResponse>builder()
                .result(vocabularyService.createVocabularyTopic(request)).build();
    }

    @GetMapping
    public ApiResponse<List<VocabularyTopicResponse>> getTopics() {
        return ApiResponse.<List<VocabularyTopicResponse>>builder()
                .result(vocabularyService.getAllVocabularyTopic())
                .build();
    }

    @GetMapping("/{topicId}/flashcards")
    public ApiResponse<List<FlashCardResponse>> getFlashcards(@PathVariable Long topicId) {
        return ApiResponse.<List<FlashCardResponse>>builder()
                .result(vocabularyService.getFlashcards(topicId))
                .build();
    }

    @PostMapping("/{topicId}/flashcards")
    public ApiResponse<List<FlashCardResponse>> addFlashCards(
            @PathVariable Long topicId,
            @RequestBody List<FlashCardCreationRequest> request
    ) {
        return ApiResponse.<List<FlashCardResponse>>builder()
                .result(vocabularyService.addFlashCards(topicId, request))
                .build();
    }
}
