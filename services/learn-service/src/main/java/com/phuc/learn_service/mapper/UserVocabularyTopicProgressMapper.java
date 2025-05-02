package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.response.UserVocabularyTopicProgressResponse;
import com.phuc.learn_service.entity.UserVocabularyTopicProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserVocabularyTopicProgressMapper {
    @Mapping(source = "vocabularyTopic.id", target = "vocabularyTopicId")
    UserVocabularyTopicProgressResponse toUserVocabularyTopicProgressResponse
            (UserVocabularyTopicProgress userVocabularyTopicProgress);
}

