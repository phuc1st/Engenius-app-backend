package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.response.UserFlashCardProgressResponse;
import com.phuc.learn_service.entity.UserFlashCardProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserFlashCardProgressMapper {
    @Mapping(source = "flashCard.id", target = "id")
    @Mapping(source = "flashCard.word", target = "word")
    @Mapping(source = "flashCard.image", target = "image")
    @Mapping(source = "flashCard.phonetic", target = "phonetic")
    @Mapping(source = "flashCard.audio", target = "audio")
    @Mapping(source = "flashCard.answer", target = "answer")
    UserFlashCardProgressResponse toUserFlashCardProgressResponse(UserFlashCardProgress progress);
}

