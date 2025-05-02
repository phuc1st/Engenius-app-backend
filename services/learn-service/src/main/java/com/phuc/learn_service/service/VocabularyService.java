package com.phuc.learn_service.service;

import com.phuc.learn_service.dto.request.FlashCardCreationRequest;
import com.phuc.learn_service.dto.request.VocabularyTopicCreationRequest;
import com.phuc.learn_service.dto.response.FlashCardResponse;
import com.phuc.learn_service.dto.response.VocabularyTopicResponse;
import com.phuc.learn_service.entity.FlashCard;
import com.phuc.learn_service.entity.VocabularyTopic;
import com.phuc.learn_service.exception.AppException;
import com.phuc.learn_service.exception.ErrorCode;
import com.phuc.learn_service.mapper.FlashCardMapper;
import com.phuc.learn_service.mapper.VocabularyTopicMapper;
import com.phuc.learn_service.repository.FlashCardRepository;
import com.phuc.learn_service.repository.VocabularyTopicRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VocabularyService {
    VocabularyTopicRepository vocabularyTopicRepository;
    FlashCardRepository flashCardRepository;
    VocabularyTopicMapper vocabularyTopicMapper;
    FlashCardMapper flashCardMapper;

    public VocabularyTopicResponse createVocabularyTopic(VocabularyTopicCreationRequest request) {
        if (vocabularyTopicRepository.existsByTopicName(request.getTopicName()))
            throw new AppException(ErrorCode.VOCABULARY_TOPIC_EXIST);
        VocabularyTopic vocabularyTopic = VocabularyTopic.builder()
                .topicName(request.getTopicName())
                .isNew(request.isNew())
                .build();
        return vocabularyTopicMapper.toVocabularyTopicResponse
                (vocabularyTopicRepository.save(vocabularyTopic));
    }

    public List<VocabularyTopicResponse> getAllVocabularyTopic() {
        return vocabularyTopicRepository.findAll()
                .stream().map(vocabularyTopicMapper::toVocabularyTopicResponse).toList();
    }

    public List<FlashCardResponse> getFlashcards(Long vocabularyTopicId) {
        return flashCardRepository.findByTopicId(vocabularyTopicId)
                .stream().map(flashCardMapper::toFlashCardResponse).toList();

    }

    public List<FlashCardResponse> addFlashCards(
            Long vocabularyTopicId, List<FlashCardCreationRequest> flashCardCreationRequests) {
        VocabularyTopic topic = vocabularyTopicRepository.findById(vocabularyTopicId)
                .orElseThrow(() -> new AppException(ErrorCode.VOCABULARY_TOPIC_NOT_EXIST));

        List<FlashCard> flashCardList = new ArrayList<>();
        flashCardCreationRequests.forEach(flashCardCreationRequest -> {
            FlashCard card = flashCardMapper.toFlashCard(flashCardCreationRequest);
            card.setTopic(topic);
            flashCardList.add(card);
        });
        return flashCardRepository.saveAll(flashCardList)
                .stream().map(flashCardMapper::toFlashCardResponse).toList();
    }
}
//TODO TEST REQUEST CỦA VOCAB, CAC RESPONSE MAPPING