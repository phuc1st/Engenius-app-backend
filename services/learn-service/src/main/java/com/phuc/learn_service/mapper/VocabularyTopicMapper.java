package com.phuc.learn_service.mapper;

import com.phuc.learn_service.dto.QuestionBlockDTO;
import com.phuc.learn_service.dto.QuestionDTO;
import com.phuc.learn_service.dto.ToeicPartDTO;
import com.phuc.learn_service.dto.ToeicTestDTO;
import com.phuc.learn_service.dto.response.ToeicTestSummaryResponse;
import com.phuc.learn_service.dto.response.VocabularyTopicResponse;
import com.phuc.learn_service.entity.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VocabularyTopicMapper {
    VocabularyTopicResponse toVocabularyTopicResponse(VocabularyTopic vocabularyTopic);
}

