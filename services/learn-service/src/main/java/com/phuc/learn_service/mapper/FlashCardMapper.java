package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.request.FlashCardCreationRequest;
import com.phuc.learn_service.dto.response.FlashCardResponse;
import com.phuc.learn_service.dto.response.VocabularyTopicResponse;
import com.phuc.learn_service.entity.FlashCard;
import com.phuc.learn_service.entity.VocabularyTopic;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashCardMapper {
    FlashCardResponse toFlashCardResponse(FlashCard flashCard);
    FlashCard toFlashCard(FlashCardCreationRequest request);
}

